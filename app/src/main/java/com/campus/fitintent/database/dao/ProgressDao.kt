package com.campus.fitintent.database.dao

import androidx.room.*
import com.campus.fitintent.models.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for DailyGoal operations
 */
@Dao
interface DailyGoalDao {
    @Query("SELECT * FROM daily_goals WHERE userId = :userId AND date = :date LIMIT 1")
    suspend fun getGoalByUserAndDate(userId: Long, date: Date): DailyGoal?

    @Query("SELECT * FROM daily_goals WHERE userId = :userId ORDER BY date DESC LIMIT :limit")
    fun getRecentGoals(userId: Long, limit: Int = 7): Flow<List<DailyGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: DailyGoal): Long

    @Update
    suspend fun updateGoal(goal: DailyGoal)

    @Query("""
        UPDATE daily_goals
        SET caloriesBurned = :calories,
            isCalorieGoalMet = CASE WHEN :calories >= calorieGoal THEN 1 ELSE 0 END
        WHERE id = :goalId
    """)
    suspend fun updateCalories(goalId: Long, calories: Int)

    @Query("""
        UPDATE daily_goals
        SET stepsTaken = :steps,
            isStepGoalMet = CASE WHEN :steps >= stepGoal THEN 1 ELSE 0 END
        WHERE id = :goalId
    """)
    suspend fun updateSteps(goalId: Long, steps: Int)

    @Query("""
        UPDATE daily_goals
        SET waterIntake = :water,
            isWaterGoalMet = CASE WHEN :water >= waterGoal THEN 1 ELSE 0 END
        WHERE id = :goalId
    """)
    suspend fun updateWater(goalId: Long, water: Int)

    @Query("""
        UPDATE daily_goals
        SET sleepHours = :sleep,
            isSleepGoalMet = CASE WHEN :sleep >= sleepGoal THEN 1 ELSE 0 END
        WHERE id = :goalId
    """)
    suspend fun updateSleep(goalId: Long, sleep: Float)

    @Query("""
        UPDATE daily_goals
        SET workoutMinutes = :minutes,
            isWorkoutGoalMet = CASE WHEN :minutes >= workoutMinutesGoal THEN 1 ELSE 0 END
        WHERE id = :goalId
    """)
    suspend fun updateWorkoutMinutes(goalId: Long, minutes: Int)

    @Query("""
        SELECT * FROM daily_goals
        WHERE userId = :userId
        AND date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getGoalsInDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<DailyGoal>>

    @Query("""
        SELECT COUNT(*) FROM daily_goals
        WHERE userId = :userId
        AND overallCompletionPercentage >= 100
    """)
    suspend fun getPerfectDaysCount(userId: Long): Int

    @Query("""
        SELECT AVG(overallCompletionPercentage) FROM daily_goals
        WHERE userId = :userId
        AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getAverageCompletionInRange(userId: Long, startDate: Date, endDate: Date): Float?
}

/**
 * Data Access Object for Streak operations
 */
@Dao
interface StreakDao {
    @Query("SELECT * FROM streaks WHERE userId = :userId AND type = :type AND isActive = 1 LIMIT 1")
    suspend fun getActiveStreak(userId: Long, type: StreakType): Streak?

    @Query("SELECT * FROM streaks WHERE userId = :userId AND isActive = 1")
    fun getActiveStreaksByUser(userId: Long): Flow<List<Streak>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: Streak): Long

    @Update
    suspend fun updateStreak(streak: Streak)

    @Query("""
        UPDATE streaks
        SET currentStreak = :current,
            longestStreak = MAX(longestStreak, :current),
<<<<<<< HEAD
            lastActiveDate = :date
=======
            lastActivityDate = :date
>>>>>>> 818ab1f (Updated)
        WHERE id = :streakId
    """)
    suspend fun updateStreakCount(streakId: Long, current: Int, date: Date)

    @Query("UPDATE streaks SET currentStreak = 0, isActive = 0 WHERE id = :streakId")
    suspend fun breakStreak(streakId: Long)

    @Query("SELECT MAX(longestStreak) FROM streaks WHERE userId = :userId AND type = :type")
    suspend fun getLongestStreakByType(userId: Long, type: StreakType): Int?

    @Query("SELECT SUM(currentStreak) FROM streaks WHERE userId = :userId AND isActive = 1")
    suspend fun getTotalActiveStreakDays(userId: Long): Int?
}

/**
<<<<<<< HEAD
 * Data Access Object for Badge operations
 */
@Dao
interface BadgeDao {
    @Query("SELECT * FROM badges WHERE userId = :userId ORDER BY earnedDate DESC")
    fun getBadgesByUser(userId: Long): Flow<List<Badge>>

    @Query("SELECT * FROM badges WHERE userId = :userId AND badgeType = :type LIMIT 1")
    suspend fun getBadgeByType(userId: Long, type: BadgeType): Badge?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBadge(badge: Badge): Long

    @Update
    suspend fun updateBadge(badge: Badge)

    @Query("UPDATE badges SET level = :level, progress = :progress WHERE id = :badgeId")
    suspend fun updateBadgeProgress(badgeId: Long, level: Int, progress: Float)

    @Query("SELECT COUNT(*) FROM badges WHERE userId = :userId AND isUnlocked = 1")
    suspend fun getUnlockedBadgeCount(userId: Long): Int

    @Query("SELECT COUNT(*) FROM badges WHERE userId = :userId AND level = 3")
    suspend fun getGoldBadgeCount(userId: Long): Int

    @Query("SELECT * FROM badges WHERE userId = :userId AND isUnlocked = 0")
    suspend fun getLockedBadges(userId: Long): List<Badge>

    @Query("""
        SELECT * FROM badges
        WHERE userId = :userId
        AND earnedDate BETWEEN :startDate AND :endDate
        ORDER BY earnedDate DESC
    """)
    fun getBadgesEarnedInRange(userId: Long, startDate: Date, endDate: Date): Flow<List<Badge>>
=======
 * Data Access Object for UserBadge operations (user progress on badges)
 */
@Dao
interface UserBadgeDao {
    @Query("SELECT * FROM user_badges WHERE userId = :userId ORDER BY unlockedAt DESC")
    fun getUserBadgesByUser(userId: Long): Flow<List<UserBadge>>

    // Alias method for ProgressRepository compatibility
    @Query("SELECT b.* FROM badges b INNER JOIN user_badges ub ON b.id = ub.badgeId WHERE ub.userId = :userId ORDER BY ub.unlockedAt DESC")
    fun getBadgesByUser(userId: Long): Flow<List<Badge>>

    @Query("SELECT * FROM user_badges WHERE userId = :userId AND badgeId = :badgeId LIMIT 1")
    suspend fun getUserBadgeByBadgeId(userId: Long, badgeId: Long): UserBadge?

    // Method for getting user badge by badge type (using enum ordinal as badgeId)
    suspend fun getBadgeByType(userId: Long, badgeType: BadgeType): UserBadge? {
        return getUserBadgeByBadgeId(userId, badgeType.ordinal.toLong())
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserBadge(userBadge: UserBadge): Long

    @Update
    suspend fun updateUserBadge(userBadge: UserBadge)

    @Query("UPDATE user_badges SET currentProgress = :progress WHERE id = :userBadgeId")
    suspend fun updateUserBadgeProgress(userBadgeId: Long, progress: Int)

    // Method for updating badge progress with level (ignoring level parameter for now)
    suspend fun updateBadgeProgress(userBadgeId: Long, level: Int, progress: Float) {
        updateUserBadgeProgress(userBadgeId, progress.toInt())
    }

    @Query("SELECT COUNT(*) FROM user_badges WHERE userId = :userId AND isUnlocked = 1")
    suspend fun getUnlockedBadgeCount(userId: Long): Int

    // Count gold badges (assuming LEGENDARY rarity badges are "gold")
    @Query("""
        SELECT COUNT(*) FROM user_badges ub
        INNER JOIN badges b ON ub.badgeId = b.id
        WHERE ub.userId = :userId AND ub.isUnlocked = 1 AND b.rarity = 'LEGENDARY'
    """)
    suspend fun getGoldBadgeCount(userId: Long): Int

    @Query("SELECT * FROM user_badges WHERE userId = :userId AND isUnlocked = 0")
    suspend fun getLockedUserBadges(userId: Long): List<UserBadge>

    @Query("""
        SELECT * FROM user_badges
        WHERE userId = :userId
        AND unlockedAt BETWEEN :startDate AND :endDate
        ORDER BY unlockedAt DESC
    """)
    fun getUserBadgesUnlockedInRange(userId: Long, startDate: Date, endDate: Date): Flow<List<UserBadge>>
>>>>>>> 818ab1f (Updated)
}
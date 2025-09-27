package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.UserBadgeDao
import com.campus.fitintent.database.dao.DailyGoalDao
import com.campus.fitintent.database.dao.StreakDao
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.DateUtils
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Repository for Progress tracking operations
 */
class ProgressRepository(
    private val userBadgeDao: UserBadgeDao,
    private val streakDao: StreakDao,
    private val dailyGoalDao: DailyGoalDao
) {
    /**
     * Get or create today's daily goal
     */
    suspend fun getTodayGoal(
        userId: Long,
        calorieGoal: Int? = null,
        stepGoal: Int? = null,
        waterGoal: Int? = null,
        sleepGoal: Float? = null,
        workoutGoal: Int? = null
    ): Result<DailyGoal> = withContext(Dispatchers.IO) {
        try {
            val today = DateUtils.getStartOfDay(Date())
            var goal = dailyGoalDao.getGoalByUserAndDate(userId, today)

            if (goal == null) {
                // Create new goal for today
                goal = DailyGoal(
                    userId = userId,
                    date = today,
                    calorieGoal = calorieGoal,
                    stepGoal = stepGoal,
                    waterGoal = waterGoal,
                    sleepGoal = sleepGoal,
                    workoutMinutesGoal = workoutGoal
                )
                val goalId = dailyGoalDao.insertGoal(goal)
                goal = goal.copy(id = goalId)
            }

            Result.Success(goal)
        } catch (e: Exception) {
            Result.Error("Failed to get today's goal: ${e.message}")
        }
    }

    /**
     * Update daily goal progress
     */
    suspend fun updateDailyProgress(
        userId: Long,
        calories: Int? = null,
        steps: Int? = null,
        water: Int? = null,
        sleep: Float? = null,
        workoutMinutes: Int? = null
    ): Result<DailyGoal> = withContext(Dispatchers.IO) {
        try {
            val todayResult = getTodayGoal(userId)
            if (todayResult !is Result.Success) {
                return@withContext Result.Error("Failed to get today's goal")
            }

            val goal = todayResult.data

            // Update individual metrics
            calories?.let { dailyGoalDao.updateCalories(goal.id, it) }
            steps?.let { dailyGoalDao.updateSteps(goal.id, it) }
            water?.let { dailyGoalDao.updateWater(goal.id, it) }
            sleep?.let { dailyGoalDao.updateSleep(goal.id, it) }
            workoutMinutes?.let { dailyGoalDao.updateWorkoutMinutes(goal.id, it) }

            // Get updated goal
            val updatedGoal = dailyGoalDao.getGoalByUserAndDate(userId, goal.date)
                ?: return@withContext Result.Error("Failed to update goal")

            // Calculate overall completion
            val completion = updatedGoal.calculateOverallCompletion()
            val finalGoal = updatedGoal.copy(overallCompletionPercentage = completion)
            dailyGoalDao.updateGoal(finalGoal)

            // Check for badge achievements
            checkAndAwardBadges(userId, finalGoal)

            Result.Success(finalGoal)
        } catch (e: Exception) {
            Result.Error("Failed to update daily progress: ${e.message}")
        }
    }

    /**
     * Get recent daily goals
     */
    fun getRecentGoals(userId: Long, days: Int = 7): Flow<List<DailyGoal>> {
        return dailyGoalDao.getRecentGoals(userId, days)
    }

    /**
     * Update streak
     */
    suspend fun updateStreak(
        userId: Long,
        type: StreakType,
        increment: Boolean = true
    ): Result<Streak> = withContext(Dispatchers.IO) {
        try {
            var streak = streakDao.getActiveStreak(userId, type)

            if (streak == null) {
                // Create new streak
                streak = Streak(
                    userId = userId,
                    type = type,
                    currentStreak = if (increment) 1 else 0,
                    longestStreak = if (increment) 1 else 0,
                    lastActivityDate = Date(),
                    startDate = Date(),
                    isActive = true
                )
                val streakId = streakDao.insertStreak(streak)
                streak = streak.copy(id = streakId)
            } else {
                val lastDate = streak.lastActivityDate
                val today = Date()

                if (lastDate != null && DateUtils.isYesterday(lastDate)) {
                    // Continue streak
                    if (increment) {
                        val newCount = streak.currentStreak + 1
                        streakDao.updateStreakCount(streak.id, newCount, today)
                        streak = streak.copy(
                            currentStreak = newCount,
                            longestStreak = maxOf(streak.longestStreak, newCount),
                            lastActivityDate = today
                        )
                    }
                } else if (lastDate != null && DateUtils.isToday(lastDate)) {
                    // Already updated today
                    // Do nothing
                } else {
                    // Break streak if not consecutive
                    if (increment) {
                        streakDao.updateStreakCount(streak.id, 1, today)
                        streak = streak.copy(
                            currentStreak = 1,
                            lastActivityDate = today
                        )
                    } else {
                        streakDao.breakStreak(streak.id)
                        streak = streak.copy(
                            currentStreak = 0,
                            isActive = false
                        )
                    }
                }
            }

            // Check for streak badges
            checkStreakBadges(userId, streak)

            Result.Success(streak)
        } catch (e: Exception) {
            Result.Error("Failed to update streak: ${e.message}")
        }
    }

    /**
     * Get user badges (Badge objects with user's badge info)
     */
    fun getUserBadges(userId: Long): Flow<List<Badge>> {
        return userBadgeDao.getBadgesByUser(userId)
    }

    /**
     * Get user badge records (UserBadge objects with progress and unlock status)
     */
    fun getUserBadgeRecords(userId: Long): Flow<List<UserBadge>> {
        return userBadgeDao.getUserBadgesByUser(userId)
    }

    /**
     * Get current progress for a specific badge
     */
    fun getBadgeProgress(userId: Long, badgeId: Long): Flow<Int> = try {
        // Return current progress from UserBadge
        userBadgeDao.getUserBadgesByUser(userId).map { userBadges: List<UserBadge> ->
            userBadges.find { it.badgeId == badgeId }?.currentProgress ?: 0
        }
    } catch (e: Exception) {
        kotlinx.coroutines.flow.flowOf(0)
    }

    /**
     * Update badge progress for BadgeManager compatibility
     */
    suspend fun updateBadgeProgress(userId: Long, badgeId: Long, progress: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val userBadge = userBadgeDao.getUserBadgeByBadgeId(userId, badgeId)
            if (userBadge != null) {
                userBadgeDao.updateUserBadgeProgress(userBadge.id, progress)
            } else {
                // Create new user badge if doesn't exist
                val newUserBadge = UserBadge(
                    userId = userId,
                    badgeId = badgeId,
                    currentProgress = progress,
                    isUnlocked = false
                )
                userBadgeDao.insertUserBadge(newUserBadge)
            }
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to update badge progress: ${e.message}")
        }
    }

    /**
     * Mark a badge as unlocked for the user
     */
    suspend fun unlockUserBadge(userId: Long, badgeId: Long, unlockedAt: Date): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val userBadge = userBadgeDao.getUserBadgeByBadgeId(userId, badgeId)
            if (userBadge != null) {
                val updatedBadge = userBadge.copy(
                    isUnlocked = true,
                    unlockedAt = unlockedAt,
                    updatedAt = Date()
                )
                userBadgeDao.updateUserBadge(updatedBadge)
            } else {
                // Create new unlocked user badge
                val newUserBadge = UserBadge(
                    userId = userId,
                    badgeId = badgeId,
                    currentProgress = 100,
                    isUnlocked = true,
                    unlockedAt = unlockedAt
                )
                userBadgeDao.insertUserBadge(newUserBadge)
            }
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to unlock badge: ${e.message}")
        }
    }

    /**
     * Add points to user (placeholder - points system not fully implemented yet)
     */
    suspend fun addUserPoints(userId: Long, points: Int, reason: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            // TODO: Implement user points system
            // For now, just return success to avoid compilation errors
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to add user points: ${e.message}")
        }
    }

    /**
     * Award badge to user
     */
    suspend fun awardBadge(
        userId: Long,
        badgeType: BadgeType,
        level: Int = 1
    ): Result<UserBadge> = withContext(Dispatchers.IO) {
        try {
            var userBadge = userBadgeDao.getBadgeByType(userId, badgeType)

            if (userBadge == null) {
                // Create new user badge
                userBadge = UserBadge(
                    userId = userId,
                    badgeId = badgeType.ordinal.toLong(), // Use enum ordinal as badge ID for now
                    currentProgress = 100,
                    isUnlocked = true,
                    unlockedAt = Date()
                )
                val userBadgeId = userBadgeDao.insertUserBadge(userBadge)
                userBadge = userBadge.copy(id = userBadgeId)
            } else if (!userBadge.isUnlocked) {
                // Unlock existing badge
                userBadgeDao.updateBadgeProgress(userBadge.id, 1, 100f)
                userBadge = userBadge.copy(
                    currentProgress = 100,
                    isUnlocked = true,
                    unlockedAt = Date(),
                    updatedAt = Date()
                )
                userBadgeDao.updateUserBadge(userBadge)
            }

            Result.Success(userBadge)
        } catch (e: Exception) {
            Result.Error("Failed to award badge: ${e.message}")
        }
    }

    /**
     * Check and award badges based on daily goal
     */
    private suspend fun checkAndAwardBadges(userId: Long, goal: DailyGoal) {
        // Perfect day badge
        if (goal.overallCompletionPercentage >= 100f) {
            awardBadge(userId, BadgeType.GOAL_GETTER, 1)

            // Check for perfect week
            val weekGoals = dailyGoalDao.getGoalsInDateRange(
                userId,
                DateUtils.getDateDaysAgo(7),
                Date()
            )
            // This would need to be collected from Flow in actual implementation
        }

        // Specific badges
        if (goal.isCalorieGoalMet) {
            val totalDays = dailyGoalDao.getPerfectDaysCount(userId)
            when {
                totalDays >= 30 -> awardBadge(userId, BadgeType.CALORIE_CRUSHER, 3)
                totalDays >= 7 -> awardBadge(userId, BadgeType.CALORIE_CRUSHER, 2)
                totalDays >= 1 -> awardBadge(userId, BadgeType.CALORIE_CRUSHER, 1)
            }
        }

        if (goal.isStepGoalMet && goal.stepGoal != null && goal.stepGoal >= 10000) {
            awardBadge(userId, BadgeType.STEP_MASTER, 1)
        }

        if (goal.isWaterGoalMet) {
            awardBadge(userId, BadgeType.WATER_CHAMPION, 1)
        }
    }

    /**
     * Check and award streak badges
     */
    private suspend fun checkStreakBadges(userId: Long, streak: Streak) {
        when (streak.type) {
            StreakType.WORKOUT -> {
                when {
                    streak.currentStreak >= 100 -> awardBadge(userId, BadgeType.CONSISTENCY_KING, 3)
                    streak.currentStreak >= 30 -> awardBadge(userId, BadgeType.MONTH_MASTER, 2)
                    streak.currentStreak >= 7 -> awardBadge(userId, BadgeType.WEEK_WARRIOR, 1)
                }
            }
            StreakType.HABIT_COMPLETION -> {
                when {
                    streak.currentStreak >= 21 -> awardBadge(userId, BadgeType.HABIT_MASTER, 3)
                    streak.currentStreak >= 7 -> awardBadge(userId, BadgeType.HABIT_BUILDER, 2)
                    streak.currentStreak >= 1 -> awardBadge(userId, BadgeType.HABIT_STARTER, 1)
                }
            }
            else -> {
                // Handle other streak types
            }
        }
    }

    /**
     * Get overall progress statistics
     */
    suspend fun getProgressStats(userId: Long): Result<ProgressStats> = withContext(Dispatchers.IO) {
        try {
            val unlockedBadges = userBadgeDao.getUnlockedBadgeCount(userId)
            val goldBadges = userBadgeDao.getGoldBadgeCount(userId)
            val perfectDays = dailyGoalDao.getPerfectDaysCount(userId)
            val totalStreakDays = streakDao.getTotalActiveStreakDays(userId) ?: 0

            val avgCompletion = dailyGoalDao.getAverageCompletionInRange(
                userId,
                DateUtils.getDateDaysAgo(30),
                Date()
            ) ?: 0f

            Result.Success(
                ProgressStats(
                    totalBadges = unlockedBadges,
                    goldBadges = goldBadges,
                    perfectDays = perfectDays,
                    totalStreakDays = totalStreakDays,
                    averageCompletion = avgCompletion
                )
            )
        } catch (e: Exception) {
            Result.Error("Failed to get progress stats: ${e.message}")
        }
    }
}

data class ProgressStats(
    val totalBadges: Int,
    val goldBadges: Int,
    val perfectDays: Int,
    val totalStreakDays: Int,
    val averageCompletion: Float
)
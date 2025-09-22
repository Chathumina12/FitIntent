package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.BadgeDao
import com.campus.fitintent.database.dao.DailyGoalDao
import com.campus.fitintent.database.dao.StreakDao
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.DateUtils
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Repository for Progress tracking operations
 */
class ProgressRepository(
    private val badgeDao: BadgeDao,
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
                    lastActiveDate = Date(),
                    startDate = Date(),
                    isActive = true
                )
                val streakId = streakDao.insertStreak(streak)
                streak = streak.copy(id = streakId)
            } else {
                val lastDate = streak.lastActiveDate
                val today = Date()

                if (lastDate != null && DateUtils.isYesterday(lastDate)) {
                    // Continue streak
                    if (increment) {
                        val newCount = streak.currentStreak + 1
                        streakDao.updateStreakCount(streak.id, newCount, today)
                        streak = streak.copy(
                            currentStreak = newCount,
                            longestStreak = maxOf(streak.longestStreak, newCount),
                            lastActiveDate = today
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
                            lastActiveDate = today
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
     * Get user badges
     */
    fun getUserBadges(userId: Long): Flow<List<Badge>> {
        return badgeDao.getBadgesByUser(userId)
    }

    /**
     * Award badge to user
     */
    suspend fun awardBadge(
        userId: Long,
        badgeType: BadgeType,
        level: Int = 1
    ): Result<Badge> = withContext(Dispatchers.IO) {
        try {
            var badge = badgeDao.getBadgeByType(userId, badgeType)

            if (badge == null) {
                // Create new badge
                badge = Badge(
                    userId = userId,
                    badgeType = badgeType,
                    earnedDate = Date(),
                    level = level,
                    progress = 100f,
                    isUnlocked = true
                )
                val badgeId = badgeDao.insertBadge(badge)
                badge = badge.copy(id = badgeId)
            } else if (badge.level < level) {
                // Upgrade existing badge
                badgeDao.updateBadgeProgress(badge.id, level, 100f)
                badge = badge.copy(level = level, progress = 100f)
            }

            Result.Success(badge)
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
            val unlockedBadges = badgeDao.getUnlockedBadgeCount(userId)
            val goldBadges = badgeDao.getGoldBadgeCount(userId)
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
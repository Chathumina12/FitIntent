package com.campus.fitintent.utils

import com.campus.fitintent.models.*
import java.util.*

/**
 * Utility class for calculating points and rewards in the FitIntent gamification system
 *
 * Point System:
 * - Habit Completion: 10 points
 * - Workout Completion: 50 points (base) + difficulty bonus
 * - Streak Milestones: 5-100 points based on streak length
 * - Badge Unlocks: 100-500 points based on badge rarity
 * - Daily Goal Achievement: 25 points
 * - Weekly Goal Achievement: 100 points
 * - Nutrition Tip Favorites: 2 points
 * - Profile Completion: 50 points
 */
object PointCalculator {

    // Base point values
    private const val HABIT_COMPLETION_POINTS = 10
    private const val WORKOUT_BASE_POINTS = 50
    private const val DAILY_GOAL_POINTS = 25
    private const val WEEKLY_GOAL_POINTS = 100
    private const val NUTRITION_FAVORITE_POINTS = 2
    private const val PROFILE_COMPLETION_POINTS = 50

    // Streak milestone points
    private val STREAK_MILESTONES = mapOf(
        3 to 15,    // 3 days: 15 points
        7 to 50,    // 1 week: 50 points
        14 to 100,  // 2 weeks: 100 points
        21 to 200,  // 3 weeks: 200 points (habit formation)
        30 to 300,  // 1 month: 300 points
        60 to 500,  // 2 months: 500 points
        90 to 750,  // 3 months: 750 points
        180 to 1000, // 6 months: 1000 points
        365 to 2000  // 1 year: 2000 points
    )

    // Badge rarity points
    private val BADGE_POINTS = mapOf(
        BadgeRarity.COMMON to 100,
        BadgeRarity.RARE to 200,
        BadgeRarity.EPIC to 350,
        BadgeRarity.LEGENDARY to 500
    )

    /**
     * Calculate points for habit completion
     */
    fun calculateHabitPoints(habit: Habit, isConsecutiveDays: Int = 1): Int {
        var points = HABIT_COMPLETION_POINTS

        // Bonus for consecutive days
        when (isConsecutiveDays) {
            in 3..6 -> points += 5   // 3-6 days: +5 bonus
            in 7..13 -> points += 10 // 1 week+: +10 bonus
            in 14..20 -> points += 15 // 2 weeks+: +15 bonus
            21 -> points += 25       // 21 days (habit formed): +25 bonus
        }

        return points
    }

    /**
     * Calculate points for workout completion
     */
    fun calculateWorkoutPoints(workout: Workout, completionTime: Int? = null): Int {
        var points = WORKOUT_BASE_POINTS

        // Difficulty bonus
        points += when (workout.difficulty) {
            DifficultyLevel.BEGINNER -> 0
            DifficultyLevel.INTERMEDIATE -> 10
            DifficultyLevel.ADVANCED -> 20
            DifficultyLevel.EXPERT -> 30
        }

        // Duration bonus (every 10 minutes adds 5 points)
        points += (workout.durationMinutes / 10) * 5

        // Category bonus
        points += when (workout.category) {
            WorkoutCategory.HIIT -> 15      // High intensity bonus
            WorkoutCategory.STRENGTH -> 10   // Strength training bonus
            WorkoutCategory.CARDIO -> 8      // Cardio bonus
            WorkoutCategory.FLEXIBILITY -> 5 // Flexibility bonus
            else -> 0
        }

        // Early completion bonus (if completed in less than estimated time)
        completionTime?.let { actualTime ->
            if (actualTime < workout.durationMinutes) {
                points += 10
            }
        }

        return points
    }

    /**
     * Calculate points for streak milestones
     */
    fun calculateStreakMilestonePoints(streakDays: Int): Int {
        return STREAK_MILESTONES[streakDays] ?: 0
    }

    /**
     * Calculate points for badge unlock
     */
    fun calculateBadgePoints(badge: Badge): Int {
        return BADGE_POINTS[badge.rarity] ?: 100
    }

    /**
     * Calculate points for daily goal achievement
     */
    fun calculateDailyGoalPoints(goal: DailyGoal, completionPercentage: Float): Int {
        return when {
            completionPercentage >= 1.0f -> DAILY_GOAL_POINTS + 10 // 100%+ completion bonus
            completionPercentage >= 0.8f -> DAILY_GOAL_POINTS      // 80%+ completion
            else -> 0
        }
    }

    /**
     * Calculate points for weekly goal achievement
     */
    fun calculateWeeklyGoalPoints(completionPercentage: Float): Int {
        return when {
            completionPercentage >= 1.0f -> WEEKLY_GOAL_POINTS + 25 // 100%+ completion bonus
            completionPercentage >= 0.8f -> WEEKLY_GOAL_POINTS      // 80%+ completion
            else -> 0
        }
    }

    /**
     * Calculate points for nutrition tip favorites
     */
    fun calculateNutritionFavoritePoints(): Int {
        return NUTRITION_FAVORITE_POINTS
    }

    /**
     * Calculate points for profile completion milestones
     */
    fun calculateProfileCompletionPoints(completionPercentage: Float): Int {
        return when {
            completionPercentage >= 1.0f -> PROFILE_COMPLETION_POINTS
            completionPercentage >= 0.75f -> PROFILE_COMPLETION_POINTS / 2
            completionPercentage >= 0.5f -> PROFILE_COMPLETION_POINTS / 4
            else -> 0
        }
    }

    /**
     * Calculate total weekly points for a user
     */
    fun calculateWeeklyPoints(
        habitsCompleted: Int,
        workoutsCompleted: List<Workout>,
        streakMilestones: List<Int>,
        badgesUnlocked: List<Badge>,
        dailyGoalsAchieved: Int,
        nutritionFavorites: Int
    ): Int {
        var totalPoints = 0

        // Habit points
        totalPoints += habitsCompleted * HABIT_COMPLETION_POINTS

        // Workout points
        totalPoints += workoutsCompleted.sumOf { calculateWorkoutPoints(it) }

        // Streak milestone points
        totalPoints += streakMilestones.sumOf { calculateStreakMilestonePoints(it) }

        // Badge points
        totalPoints += badgesUnlocked.sumOf { calculateBadgePoints(it) }

        // Daily goal points
        totalPoints += dailyGoalsAchieved * DAILY_GOAL_POINTS

        // Nutrition favorite points
        totalPoints += nutritionFavorites * NUTRITION_FAVORITE_POINTS

        return totalPoints
    }

    /**
     * Calculate user level based on total points
     * Level system: Every 1000 points = 1 level
     */
    fun calculateUserLevel(totalPoints: Int): Int {
        return (totalPoints / 1000) + 1
    }

    /**
     * Calculate points needed for next level
     */
    fun calculatePointsToNextLevel(totalPoints: Int): Int {
        val currentLevel = calculateUserLevel(totalPoints)
        val pointsForNextLevel = currentLevel * 1000
        return pointsForNextLevel - totalPoints
    }

    /**
     * Calculate progress percentage to next level
     */
    fun calculateLevelProgress(totalPoints: Int): Float {
        val currentLevel = calculateUserLevel(totalPoints) - 1
        val pointsInCurrentLevel = totalPoints - (currentLevel * 1000)
        return (pointsInCurrentLevel / 1000f).coerceAtMost(1.0f)
    }

    /**
     * Get level title based on user level
     */
    fun getLevelTitle(level: Int): String {
        return when (level) {
            1 -> "Beginner"
            in 2..5 -> "Novice"
            in 6..10 -> "Enthusiast"
            in 11..20 -> "Expert"
            in 21..35 -> "Master"
            in 36..50 -> "Champion"
            in 51..75 -> "Legend"
            in 76..100 -> "Hero"
            else -> "Grandmaster"
        }
    }

    /**
     * Calculate daily activity streak bonus
     */
    fun calculateDailyActivityStreakBonus(consecutiveDays: Int): Int {
        return when (consecutiveDays) {
            in 1..2 -> 0
            in 3..6 -> 5
            in 7..13 -> 10
            in 14..20 -> 15
            in 21..29 -> 20
            in 30..59 -> 25
            in 60..89 -> 30
            in 90..179 -> 40
            in 180..364 -> 50
            else -> 75 // 365+ days
        }
    }

    /**
     * Check if user achieved a perfect week (all goals met)
     */
    fun calculatePerfectWeekBonus(
        habitsCompletedThisWeek: Int,
        targetHabitsPerWeek: Int,
        workoutsCompletedThisWeek: Int,
        targetWorkoutsPerWeek: Int
    ): Int {
        return if (habitsCompletedThisWeek >= targetHabitsPerWeek &&
                  workoutsCompletedThisWeek >= targetWorkoutsPerWeek) {
            200 // Perfect week bonus
        } else {
            0
        }
    }

    /**
     * Generate activity summary with points breakdown
     */
    data class PointsBreakdown(
        val habitPoints: Int = 0,
        val workoutPoints: Int = 0,
        val streakBonusPoints: Int = 0,
        val badgePoints: Int = 0,
        val goalPoints: Int = 0,
        val perfectWeekBonus: Int = 0,
        val totalPoints: Int = 0
    )

    /**
     * Calculate detailed points breakdown for reporting
     */
    fun calculatePointsBreakdown(
        habitsCompleted: Int,
        workoutsCompleted: List<Workout>,
        streakBonus: Int,
        badgesUnlocked: List<Badge>,
        goalsAchieved: Int,
        perfectWeekBonus: Int = 0
    ): PointsBreakdown {
        val habitPoints = habitsCompleted * HABIT_COMPLETION_POINTS
        val workoutPoints = workoutsCompleted.sumOf { calculateWorkoutPoints(it) }
        val badgePoints = badgesUnlocked.sumOf { calculateBadgePoints(it) }
        val goalPoints = goalsAchieved * DAILY_GOAL_POINTS

        val totalPoints = habitPoints + workoutPoints + streakBonus +
                         badgePoints + goalPoints + perfectWeekBonus

        return PointsBreakdown(
            habitPoints = habitPoints,
            workoutPoints = workoutPoints,
            streakBonusPoints = streakBonus,
            badgePoints = badgePoints,
            goalPoints = goalPoints,
            perfectWeekBonus = perfectWeekBonus,
            totalPoints = totalPoints
        )
    }
}
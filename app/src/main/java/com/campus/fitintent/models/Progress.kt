package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Daily goals for tracking user progress
 */
@Entity(
    tableName = "daily_goals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index(value = ["userId", "date"], unique = true)
    ]
)
data class DailyGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val date: Date,

    // Goals
    val calorieGoal: Int? = null,
    val stepGoal: Int? = null,
    val waterGoal: Int? = null, // in ml
    val sleepGoal: Float? = null, // in hours
    val workoutMinutesGoal: Int? = null,

    // Actual values
    val caloriesBurned: Int = 0,
    val stepsTaken: Int = 0,
    val waterIntake: Int = 0, // in ml
    val sleepHours: Float = 0f,
    val workoutMinutes: Int = 0,

    // Completion status
    val isCalorieGoalMet: Boolean = false,
    val isStepGoalMet: Boolean = false,
    val isWaterGoalMet: Boolean = false,
    val isSleepGoalMet: Boolean = false,
    val isWorkoutGoalMet: Boolean = false,

    val overallCompletionPercentage: Float = 0f,
    val updatedAt: Date = Date()
) {
    fun calculateOverallCompletion(): Float {
        var totalGoals = 0
        var metGoals = 0

        if (calorieGoal != null) {
            totalGoals++
            if (isCalorieGoalMet) metGoals++
        }
        if (stepGoal != null) {
            totalGoals++
            if (isStepGoalMet) metGoals++
        }
        if (waterGoal != null) {
            totalGoals++
            if (isWaterGoalMet) metGoals++
        }
        if (sleepGoal != null) {
            totalGoals++
            if (isSleepGoalMet) metGoals++
        }
        if (workoutMinutesGoal != null) {
            totalGoals++
            if (isWorkoutGoalMet) metGoals++
        }

        return if (totalGoals > 0) {
            (metGoals.toFloat() / totalGoals.toFloat()) * 100
        } else 0f
    }
}

/**
 * Streaks for tracking consistency
 */
@Entity(
    tableName = "streaks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Streak(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val type: StreakType,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: Date? = null,
    val startDate: Date = Date(),
    val isActive: Boolean = true
)

enum class StreakType {
    LOGIN,
    WORKOUT,
    HABIT_COMPLETION,
    DAILY_GOALS,
    WATER_INTAKE,
    MEDITATION
}

/**
 * Achievement badges for gamification
 */
@Entity(
    tableName = "badges",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index(value = ["userId", "badgeType"], unique = true)
    ]
)
data class Badge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val badgeType: BadgeType,
    val earnedDate: Date = Date(),
    val progress: Float = 100f, // For progressive badges
    val level: Int = 1, // Bronze=1, Silver=2, Gold=3
    val isUnlocked: Boolean = true
)

enum class BadgeType {
    // Workout badges
    FIRST_WORKOUT,
    WORKOUT_WARRIOR, // 50 workouts
    CENTURY_CLUB, // 100 workouts

    // Streak badges
    WEEK_WARRIOR, // 7-day streak
    MONTH_MASTER, // 30-day streak
    CONSISTENCY_KING, // 100-day streak

    // Habit badges
    HABIT_STARTER,
    HABIT_BUILDER,
    HABIT_MASTER,

    // Goal badges
    GOAL_GETTER,
    PERFECT_WEEK,
    PERFECT_MONTH,

    // Special badges
    EARLY_BIRD, // Morning workouts
    NIGHT_OWL, // Evening workouts
    WATER_CHAMPION,
    CALORIE_CRUSHER,
    STEP_MASTER
}
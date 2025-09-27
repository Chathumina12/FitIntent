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
    val lastActivityDate: Date? = null,
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
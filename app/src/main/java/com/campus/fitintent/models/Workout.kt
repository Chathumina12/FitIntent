package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Predefined workout templates
 */
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    val description: String,
    val category: WorkoutCategory,
    val difficulty: DifficultyLevel,
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val equipment: String? = null, // Comma-separated list
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val instructions: String, // JSON or formatted text
    val targetMuscles: String? = null, // Comma-separated list
    val isCustom: Boolean = false,
    val createdBy: Long? = null // User ID if custom workout
)

enum class WorkoutCategory {
    CARDIO,
    STRENGTH,
    FLEXIBILITY,
    HIIT,
    YOGA,
    PILATES,
    SPORTS,
    RECOVERY,
    FULL_BODY
}

enum class DifficultyLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}

/**
 * Individual exercise within a workout
 */
@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutId")]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutId: Long,
    val name: String,
    val description: String,
    val orderIndex: Int, // Order in workout
    val sets: Int? = null,
    val reps: Int? = null,
    val durationSeconds: Int? = null,
    val restSeconds: Int = 30,
    val targetHeartRate: Int? = null,
    val notes: String? = null,
    val imageUrl: String? = null,
    val videoUrl: String? = null
)

/**
 * Workout session tracking
 */
@Entity(
    tableName = "workout_sessions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("userId"), Index("workoutId")]
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val workoutId: Long?,
    val startTime: Date,
    val endTime: Date? = null,
    val durationMinutes: Int? = null,
    val caloriesBurned: Int? = null,
    val averageHeartRate: Int? = null,
    val maxHeartRate: Int? = null,
    val notes: String? = null,
    val feelingRating: Int? = null, // 1-5 scale
    val difficultyRating: Int? = null, // 1-5 scale
    val isCompleted: Boolean = false,
    val completionPercentage: Float = 0f
) {
    fun calculateDuration(): Int {
        return if (startTime != null && endTime != null) {
            ((endTime.time - startTime.time) / 1000 / 60).toInt()
        } else durationMinutes ?: 0
    }
}
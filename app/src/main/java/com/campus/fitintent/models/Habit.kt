package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Habit entity for tracking user habits
 */
@Entity(
    tableName = "habits",
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
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val name: String,
    val description: String,
    val category: HabitCategory,
    val targetDays: Int = 21, // Default 21-day habit formation

    // Schedule
    val frequency: HabitFrequency = HabitFrequency.DAILY,
    val reminderTime: String? = null, // Format: "HH:mm"
    val reminderEnabled: Boolean = false,

    // Tracking
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val completedDays: Int = 0,
    val skippedDays: Int = 0,

    // Status
    val isActive: Boolean = true,
    val isPaused: Boolean = false,
    val isCompleted: Boolean = false,

    // Metadata
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val completedAt: Date? = null,
    val color: String = "#4CAF50", // Default green color
    val icon: String? = null
) {
    // Calculate completion percentage
    fun getCompletionPercentage(): Float {
        return if (targetDays > 0) {
            (completedDays.toFloat() / targetDays.toFloat()) * 100
        } else 0f
    }

    // Check if habit is on track
    fun isOnTrack(): Boolean {
        val totalDays = completedDays + skippedDays
        val expectedCompletionRate = 0.8f // 80% completion rate expected
        return if (totalDays > 0) {
            (completedDays.toFloat() / totalDays.toFloat()) >= expectedCompletionRate
        } else true
    }
}

enum class HabitCategory {
    EXERCISE,
    NUTRITION,
    HYDRATION,
    SLEEP,
    MINDFULNESS,
    STRETCHING,
    CARDIO,
    STRENGTH,
    OTHER
}

enum class HabitFrequency {
    DAILY,
    WEEKLY,
    CUSTOM
}

/**
 * Entity for tracking daily habit logs
 */
@Entity(
    tableName = "habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("habitId"),
        Index(value = ["habitId", "date"], unique = true)
    ]
)
data class HabitLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val habitId: Long,
    val date: Date,
    val status: HabitLogStatus,
    val notes: String? = null,
    val completedAt: Date? = null,
    val skippedReason: String? = null
)

enum class HabitLogStatus {
    PENDING,
    COMPLETED,
    SKIPPED,
    PARTIAL
}
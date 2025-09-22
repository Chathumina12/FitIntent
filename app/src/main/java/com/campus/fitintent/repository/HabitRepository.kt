package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.HabitDao
import com.campus.fitintent.database.dao.HabitLogDao
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.DateUtils
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Calendar

/**
 * Repository for Habit-related operations
 */
class HabitRepository(
    private val habitDao: HabitDao,
    private val habitLogDao: HabitLogDao
) {
    /**
     * Get all active habits for a user
     */
    fun getActiveHabits(userId: Long): Flow<List<Habit>> {
        return habitDao.getActiveHabitsByUser(userId)
    }

    /**
     * Create a new habit
     */
    suspend fun createHabit(
        userId: Long,
        name: String,
        description: String,
        category: HabitCategory,
        frequency: HabitFrequency = HabitFrequency.DAILY,
        reminderTime: String? = null,
        reminderEnabled: Boolean = false,
        color: String = "#4CAF50"
    ): Result<Habit> = withContext(Dispatchers.IO) {
        try {
            // Validate input
            if (name.isBlank()) {
                return@withContext Result.Error("Habit name is required")
            }

            if (name.length > 50) {
                return@withContext Result.Error("Habit name must be less than 50 characters")
            }

            val habit = Habit(
                userId = userId,
                name = name,
                description = description,
                category = category,
                frequency = frequency,
                reminderTime = reminderTime,
                reminderEnabled = reminderEnabled && !reminderTime.isNullOrBlank(),
                color = color,
                createdAt = Date(),
                updatedAt = Date()
            )

            val habitId = habitDao.insertHabit(habit)
            val createdHabit = habit.copy(id = habitId)

            // Create initial log entry for today
            createTodayLogIfNotExists(habitId)

            Result.Success(createdHabit)
        } catch (e: Exception) {
            Result.Error("Failed to create habit: ${e.message}")
        }
    }

    /**
     * Mark habit as completed for today
     */
    suspend fun completeHabitForToday(habitId: Long): Result<HabitLog> = withContext(Dispatchers.IO) {
        try {
            val today = DateUtils.getStartOfDay(Date())
            var log = habitLogDao.getLogByHabitAndDate(habitId, today)

            if (log == null) {
                // Create log if doesn't exist
                log = HabitLog(
                    habitId = habitId,
                    date = today,
                    status = HabitLogStatus.COMPLETED,
                    completedAt = Date()
                )
                val logId = habitLogDao.insertLog(log)
                log = log.copy(id = logId)
            } else {
                // Update existing log
                habitLogDao.updateLogStatus(
                    log.id,
                    HabitLogStatus.COMPLETED,
                    Date()
                )
                log = log.copy(
                    status = HabitLogStatus.COMPLETED,
                    completedAt = Date()
                )
            }

            // Update habit statistics
            updateHabitStatistics(habitId)

            Result.Success(log)
        } catch (e: Exception) {
            Result.Error("Failed to complete habit: ${e.message}")
        }
    }

    /**
     * Skip habit for today with reason
     */
    suspend fun skipHabitForToday(habitId: Long, reason: String? = null): Result<HabitLog> = withContext(Dispatchers.IO) {
        try {
            val today = DateUtils.getStartOfDay(Date())
            var log = habitLogDao.getLogByHabitAndDate(habitId, today)

            if (log == null) {
                log = HabitLog(
                    habitId = habitId,
                    date = today,
                    status = HabitLogStatus.SKIPPED,
                    skippedReason = reason
                )
                val logId = habitLogDao.insertLog(log)
                log = log.copy(id = logId)
            } else {
                habitLogDao.updateLogStatus(log.id, HabitLogStatus.SKIPPED, null)
                log = log.copy(
                    status = HabitLogStatus.SKIPPED,
                    skippedReason = reason
                )
            }

            // Update habit statistics
            updateHabitStatistics(habitId)

            Result.Success(log)
        } catch (e: Exception) {
            Result.Error("Failed to skip habit: ${e.message}")
        }
    }

    /**
     * Update habit statistics including streaks
     */
    private suspend fun updateHabitStatistics(habitId: Long) {
        val habit = habitDao.getHabitById(habitId) ?: return

        // Calculate streak
        val currentStreak = calculateCurrentStreak(habitId)
        val completedDays = habitLogDao.getCompletedCount(habitId)
        val skippedDays = habitLogDao.getSkippedCount(habitId)

        // Update habit
        val updatedHabit = habit.copy(
            currentStreak = currentStreak,
            longestStreak = maxOf(habit.longestStreak, currentStreak),
            completedDays = completedDays,
            skippedDays = skippedDays,
            isCompleted = completedDays >= habit.targetDays,
            completedAt = if (completedDays >= habit.targetDays && habit.completedAt == null) {
                Date()
            } else habit.completedAt,
            updatedAt = Date()
        )

        habitDao.updateHabit(updatedHabit)
    }

    /**
     * Calculate current streak for a habit
     */
    private suspend fun calculateCurrentStreak(habitId: Long): Int {
        val logs = habitLogDao.getRecentLogs(
            habitId,
            DateUtils.getDateDaysAgo(30)
        )

        if (logs.isEmpty()) return 0

        var streak = 0
        val calendar = Calendar.getInstance()
        calendar.time = DateUtils.getStartOfDay(Date())

        // Check backwards from today
        while (streak < logs.size) {
            val dateToCheck = DateUtils.getStartOfDay(calendar.time)
            val log = logs.find {
                DateUtils.isSameDay(it.date, dateToCheck)
            }

            if (log?.status == HabitLogStatus.COMPLETED) {
                streak++
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }

        return streak
    }

    /**
     * Create today's log entry if doesn't exist
     */
    private suspend fun createTodayLogIfNotExists(habitId: Long) {
        val today = DateUtils.getStartOfDay(Date())
        val existingLog = habitLogDao.getLogByHabitAndDate(habitId, today)

        if (existingLog == null) {
            val log = HabitLog(
                habitId = habitId,
                date = today,
                status = HabitLogStatus.PENDING
            )
            habitLogDao.insertLog(log)
        }
    }

    /**
     * Get habit logs for date range
     */
    fun getHabitLogs(habitId: Long): Flow<List<HabitLog>> {
        return habitLogDao.getLogsByHabit(habitId)
    }

    /**
     * Pause/Resume habit
     */
    suspend fun toggleHabitPause(habitId: Long): Result<Habit> = withContext(Dispatchers.IO) {
        try {
            val habit = habitDao.getHabitById(habitId)
                ?: return@withContext Result.Error("Habit not found")

            habitDao.setHabitPaused(habitId, !habit.isPaused)
            val updatedHabit = habit.copy(isPaused = !habit.isPaused)

            Result.Success(updatedHabit)
        } catch (e: Exception) {
            Result.Error("Failed to toggle habit pause: ${e.message}")
        }
    }

    /**
     * Delete habit
     */
    suspend fun deleteHabit(habitId: Long): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val habit = habitDao.getHabitById(habitId)
                ?: return@withContext Result.Error("Habit not found")

            habitDao.deleteHabit(habit)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to delete habit: ${e.message}")
        }
    }

    /**
     * Get habit statistics
     */
    suspend fun getHabitStats(userId: Long): Result<HabitStats> = withContext(Dispatchers.IO) {
        try {
            val activeCount = habitDao.getActiveHabitCount(userId)
            val completedCount = habitDao.getCompletedHabitCount(userId)

            Result.Success(
                HabitStats(
                    activeHabits = activeCount,
                    completedHabits = completedCount,
                    totalHabits = activeCount + completedCount
                )
            )
        } catch (e: Exception) {
            Result.Error("Failed to get habit stats: ${e.message}")
        }
    }
}

data class HabitStats(
    val activeHabits: Int,
    val completedHabits: Int,
    val totalHabits: Int
)
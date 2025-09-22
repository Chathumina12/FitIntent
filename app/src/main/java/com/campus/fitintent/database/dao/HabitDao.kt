package com.campus.fitintent.database.dao

import androidx.room.*
import com.campus.fitintent.models.Habit
import com.campus.fitintent.models.HabitLog
import com.campus.fitintent.models.HabitLogStatus
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for Habit operations
 */
@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE userId = :userId AND isActive = 1 ORDER BY createdAt DESC")
    fun getActiveHabitsByUser(userId: Long): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllHabitsByUser(userId: Long): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitById(habitId: Long): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("UPDATE habits SET currentStreak = :streak, longestStreak = MAX(longestStreak, :streak) WHERE id = :habitId")
    suspend fun updateStreak(habitId: Long, streak: Int)

    @Query("UPDATE habits SET completedDays = completedDays + 1 WHERE id = :habitId")
    suspend fun incrementCompletedDays(habitId: Long)

    @Query("UPDATE habits SET skippedDays = skippedDays + 1 WHERE id = :habitId")
    suspend fun incrementSkippedDays(habitId: Long)

    @Query("UPDATE habits SET isActive = :isActive WHERE id = :habitId")
    suspend fun setHabitActive(habitId: Long, isActive: Boolean)

    @Query("UPDATE habits SET isPaused = :isPaused WHERE id = :habitId")
    suspend fun setHabitPaused(habitId: Long, isPaused: Boolean)

    @Query("UPDATE habits SET isCompleted = 1, completedAt = :completedAt WHERE id = :habitId")
    suspend fun markHabitCompleted(habitId: Long, completedAt: Date)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE userId = :userId AND reminderEnabled = 1 AND reminderTime IS NOT NULL")
    suspend fun getHabitsWithReminders(userId: Long): List<Habit>

    @Query("SELECT COUNT(*) FROM habits WHERE userId = :userId AND isActive = 1")
    suspend fun getActiveHabitCount(userId: Long): Int

    @Query("SELECT COUNT(*) FROM habits WHERE userId = :userId AND isCompleted = 1")
    suspend fun getCompletedHabitCount(userId: Long): Int
}

/**
 * Data Access Object for HabitLog operations
 */
@Dao
interface HabitLogDao {
    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY date DESC")
    fun getLogsByHabit(habitId: Long): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getLogByHabitAndDate(habitId: Long, date: Date): HabitLog?

    @Query("SELECT * FROM habit_logs WHERE habitId IN (:habitIds) AND date = :date")
    suspend fun getLogsForHabitsOnDate(habitIds: List<Long>, date: Date): List<HabitLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: HabitLog): Long

    @Update
    suspend fun updateLog(log: HabitLog)

    @Query("UPDATE habit_logs SET status = :status, completedAt = :completedAt WHERE id = :logId")
    suspend fun updateLogStatus(logId: Long, status: HabitLogStatus, completedAt: Date?)

    @Delete
    suspend fun deleteLog(log: HabitLog)

    @Query("SELECT COUNT(*) FROM habit_logs WHERE habitId = :habitId AND status = 'COMPLETED'")
    suspend fun getCompletedCount(habitId: Long): Int

    @Query("SELECT COUNT(*) FROM habit_logs WHERE habitId = :habitId AND status = 'SKIPPED'")
    suspend fun getSkippedCount(habitId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM habit_logs
        WHERE habitId = :habitId
        AND date BETWEEN :startDate AND :endDate
        AND status = 'COMPLETED'
    """)
    suspend fun getCompletedCountInRange(habitId: Long, startDate: Date, endDate: Date): Int

    @Query("""
        SELECT * FROM habit_logs
        WHERE habitId = :habitId
        AND date >= :startDate
        ORDER BY date ASC
    """)
    suspend fun getRecentLogs(habitId: Long, startDate: Date): List<HabitLog>
}
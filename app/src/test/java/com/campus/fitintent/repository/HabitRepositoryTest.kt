package com.campus.fitintent.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.campus.fitintent.database.dao.*
import com.campus.fitintent.models.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.util.*

/**
 * Comprehensive unit tests for HabitRepository
 * Tests habit CRUD operations, streak calculations, and completion tracking
 */
class HabitRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockHabitDao: HabitDao

    @Mock
    private lateinit var mockHabitCompletionDao: HabitCompletionDao

    @Mock
    private lateinit var mockStreakDao: StreakDao

    private lateinit var habitRepository: HabitRepository

    private val testUserId = 1L
    private val testHabitId = 1L

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        habitRepository = HabitRepository(mockHabitDao, mockHabitCompletionDao, mockStreakDao)
    }

    // ========================
    // Habit CRUD Tests
    // ========================

    @Test
    fun `createHabit inserts habit and returns Result success`() = runTest {
        val habit = createTestHabit()
        whenever(mockHabitDao.insert(habit)).thenReturn(testHabitId)

        val result = habitRepository.createHabit(habit)

        assertTrue("Should return success", result.isSuccess)
        assertEquals("Should return habit ID", testHabitId, result.getOrNull())
        verify(mockHabitDao).insert(habit)
    }

    @Test
    fun `createHabit handles database exception and returns failure`() = runTest {
        val habit = createTestHabit()
        val exception = RuntimeException("Database error")
        whenever(mockHabitDao.insert(habit)).thenThrow(exception)

        val result = habitRepository.createHabit(habit)

        assertTrue("Should return failure", result.isFailure)
        assertEquals("Should contain exception message", exception, result.exceptionOrNull())
    }

    @Test
    fun `updateHabit updates habit and returns success`() = runTest {
        val habit = createTestHabit()
        whenever(mockHabitDao.update(habit)).thenReturn(1) // 1 row updated

        val result = habitRepository.updateHabit(habit)

        assertTrue("Should return success", result.isSuccess)
        verify(mockHabitDao).update(habit)
    }

    @Test
    fun `updateHabit handles no rows updated and returns failure`() = runTest {
        val habit = createTestHabit()
        whenever(mockHabitDao.update(habit)).thenReturn(0) // 0 rows updated

        val result = habitRepository.updateHabit(habit)

        assertTrue("Should return failure", result.isFailure)
        assertTrue("Should contain appropriate error message",
            result.exceptionOrNull()?.message?.contains("not found") == true)
    }

    @Test
    fun `deleteHabit deletes habit and returns success`() = runTest {
        whenever(mockHabitDao.delete(testHabitId)).thenReturn(1)

        val result = habitRepository.deleteHabit(testHabitId)

        assertTrue("Should return success", result.isSuccess)
        verify(mockHabitDao).delete(testHabitId)
    }

    @Test
    fun `getHabitById returns habit when found`() = runTest {
        val habit = createTestHabit()
        whenever(mockHabitDao.getById(testHabitId)).thenReturn(habit)

        val result = habitRepository.getHabitById(testHabitId)

        assertTrue("Should return success", result.isSuccess)
        assertEquals("Should return correct habit", habit, result.getOrNull())
    }

    @Test
    fun `getHabitById returns failure when not found`() = runTest {
        whenever(mockHabitDao.getById(testHabitId)).thenReturn(null)

        val result = habitRepository.getHabitById(testHabitId)

        assertTrue("Should return failure", result.isFailure)
    }

    @Test
    fun `getUserHabits returns flow of active habits`() = runTest {
        val habits = listOf(createTestHabit(), createTestHabit(id = 2L, name = "Test Habit 2"))
        whenever(mockHabitDao.getActiveHabitsByUser(testUserId)).thenReturn(flowOf(habits))

        val result = habitRepository.getUserHabits(testUserId)
        val resultList = mutableListOf<List<Habit>>()
        result.collect { resultList.add(it) }

        assertEquals("Should return habits list", habits, resultList.first())
        verify(mockHabitDao).getActiveHabitsByUser(testUserId)
    }

    // ========================
    // Habit Completion Tests
    // ========================

    @Test
    fun `completeHabit creates completion record and calculates streak`() = runTest {
        val date = Date()
        val completion = HabitCompletion(
            habitId = testHabitId,
            completedAt = date,
            isCompleted = true
        )

        whenever(mockHabitCompletionDao.insert(any<HabitCompletion>())).thenReturn(1L)
        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(null) // No existing streak

        val result = habitRepository.completeHabit(testHabitId, date)

        assertTrue("Should return success", result.isSuccess)

        // Verify completion was inserted
        verify(mockHabitCompletionDao).insert(argThat { habitCompletion ->
            habitCompletion.habitId == testHabitId &&
            habitCompletion.isCompleted &&
            isSameDay(habitCompletion.completedAt, date)
        })

        // Verify streak was created
        verify(mockStreakDao).insert(argThat { streak ->
            streak.habitId == testHabitId && streak.currentStreak == 1
        })
    }

    @Test
    fun `completeHabit updates existing streak when consecutive`() = runTest {
        val today = Date()
        val yesterday = Date(today.time - 86400000) // 24 hours ago

        val existingStreak = Streak(
            id = 1L,
            habitId = testHabitId,
            currentStreak = 5,
            longestStreak = 10,
            lastCompletionDate = yesterday
        )

        whenever(mockHabitCompletionDao.insert(any<HabitCompletion>())).thenReturn(1L)
        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(existingStreak)

        val result = habitRepository.completeHabit(testHabitId, today)

        assertTrue("Should return success", result.isSuccess)

        // Verify streak was updated to 6
        verify(mockStreakDao).update(argThat { streak ->
            streak.currentStreak == 6 && streak.lastCompletionDate == today
        })
    }

    @Test
    fun `completeHabit resets streak when gap exists`() = runTest {
        val today = Date()
        val threeDaysAgo = Date(today.time - 3 * 86400000) // 3 days ago

        val existingStreak = Streak(
            id = 1L,
            habitId = testHabitId,
            currentStreak = 5,
            longestStreak = 10,
            lastCompletionDate = threeDaysAgo
        )

        whenever(mockHabitCompletionDao.insert(any<HabitCompletion>())).thenReturn(1L)
        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(existingStreak)

        val result = habitRepository.completeHabit(testHabitId, today)

        assertTrue("Should return success", result.isSuccess)

        // Verify streak was reset to 1
        verify(mockStreakDao).update(argThat { streak ->
            streak.currentStreak == 1 && streak.lastCompletionDate == today
        })
    }

    @Test
    fun `uncompleteHabit removes completion and updates streak`() = runTest {
        val date = Date()
        val completion = HabitCompletion(
            id = 1L,
            habitId = testHabitId,
            completedAt = date,
            isCompleted = true
        )

        whenever(mockHabitCompletionDao.getByHabitAndDate(testHabitId, any(), any()))
            .thenReturn(completion)
        whenever(mockHabitCompletionDao.delete(1L)).thenReturn(1)

        val result = habitRepository.uncompleteHabit(testHabitId, date)

        assertTrue("Should return success", result.isSuccess)
        verify(mockHabitCompletionDao).delete(1L)
    }

    @Test
    fun `isHabitCompletedToday returns true when completed today`() = runTest {
        val today = Date()
        val completion = HabitCompletion(
            id = 1L,
            habitId = testHabitId,
            completedAt = today,
            isCompleted = true
        )

        whenever(mockHabitCompletionDao.getByHabitAndDate(eq(testHabitId), any(), any()))
            .thenReturn(completion)

        val result = habitRepository.isHabitCompletedToday(testHabitId)

        assertTrue("Should return true for today's completion", result.getOrElse { false })
    }

    @Test
    fun `isHabitCompletedToday returns false when not completed today`() = runTest {
        whenever(mockHabitCompletionDao.getByHabitAndDate(eq(testHabitId), any(), any()))
            .thenReturn(null)

        val result = habitRepository.isHabitCompletedToday(testHabitId)

        assertFalse("Should return false when not completed today", result.getOrElse { true })
    }

    // ========================
    // Streak Calculation Tests
    // ========================

    @Test
    fun `getCurrentStreak returns correct streak when exists`() = runTest {
        val streak = Streak(
            id = 1L,
            habitId = testHabitId,
            currentStreak = 15,
            longestStreak = 20,
            lastCompletionDate = Date()
        )

        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(streak)

        val result = habitRepository.getCurrentStreak(testHabitId)

        assertEquals("Should return current streak", 15, result.getOrElse { 0 })
    }

    @Test
    fun `getCurrentStreak returns zero when no streak exists`() = runTest {
        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(null)

        val result = habitRepository.getCurrentStreak(testHabitId)

        assertEquals("Should return zero when no streak", 0, result.getOrElse { -1 })
    }

    @Test
    fun `getLongestStreak returns correct longest streak`() = runTest {
        val streak = Streak(
            id = 1L,
            habitId = testHabitId,
            currentStreak = 5,
            longestStreak = 25,
            lastCompletionDate = Date()
        )

        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(streak)

        val result = habitRepository.getLongestStreak(testHabitId)

        assertEquals("Should return longest streak", 25, result.getOrElse { 0 })
    }

    @Test
    fun `calculateStreakForPeriod returns correct completion count`() = runTest {
        val startDate = Date()
        val endDate = Date(startDate.time + 7 * 86400000) // 7 days later

        val completions = listOf(
            HabitCompletion(1L, testHabitId, Date(startDate.time + 86400000), true),
            HabitCompletion(2L, testHabitId, Date(startDate.time + 2 * 86400000), true),
            HabitCompletion(3L, testHabitId, Date(startDate.time + 3 * 86400000), true)
        )

        whenever(mockHabitCompletionDao.getCompletionsBetweenDates(testHabitId, startDate, endDate))
            .thenReturn(completions)

        val result = habitRepository.calculateStreakForPeriod(testHabitId, startDate, endDate)

        assertEquals("Should return completion count", 3, result.getOrElse { 0 })
    }

    // ========================
    // Habit Statistics Tests
    // ========================

    @Test
    fun `getHabitCompletionRate returns correct rate`() = runTest {
        val totalDays = 30
        val completedDays = 21

        whenever(mockHabitCompletionDao.getCompletionCountSince(eq(testHabitId), any()))
            .thenReturn(completedDays)

        val result = habitRepository.getHabitCompletionRate(testHabitId, totalDays)

        val expectedRate = completedDays.toFloat() / totalDays
        assertEquals("Should return correct completion rate", expectedRate, result.getOrElse { 0f }, 0.01f)
    }

    @Test
    fun `getHabitCompletionsByMonth returns monthly completions`() = runTest {
        val month = 12 // December
        val year = 2023
        val completions = listOf(
            HabitCompletion(1L, testHabitId, Date(), true),
            HabitCompletion(2L, testHabitId, Date(), true)
        )

        whenever(mockHabitCompletionDao.getCompletionsByMonth(testHabitId, year, month))
            .thenReturn(completions)

        val result = habitRepository.getHabitCompletionsByMonth(testHabitId, year, month)

        assertTrue("Should return success", result.isSuccess)
        assertEquals("Should return correct completions", completions, result.getOrNull())
    }

    @Test
    fun `getTotalHabitsCompletedToday returns correct count`() = runTest {
        val expectedCount = 5
        whenever(mockHabitCompletionDao.getTotalCompletionsToday(testUserId))
            .thenReturn(expectedCount)

        val result = habitRepository.getTotalHabitsCompletedToday(testUserId)

        assertEquals("Should return today's completion count", expectedCount, result.getOrElse { 0 })
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `completeHabit handles database exception gracefully`() = runTest {
        val date = Date()
        val exception = RuntimeException("Database connection failed")
        whenever(mockHabitCompletionDao.insert(any<HabitCompletion>())).thenThrow(exception)

        val result = habitRepository.completeHabit(testHabitId, date)

        assertTrue("Should return failure", result.isFailure)
        assertEquals("Should contain original exception", exception, result.exceptionOrNull())
    }

    @Test
    fun `streak calculation handles null last completion date`() = runTest {
        val today = Date()
        val streakWithNullDate = Streak(
            id = 1L,
            habitId = testHabitId,
            currentStreak = 5,
            longestStreak = 10,
            lastCompletionDate = null
        )

        whenever(mockHabitCompletionDao.insert(any<HabitCompletion>())).thenReturn(1L)
        whenever(mockStreakDao.getByHabitId(testHabitId)).thenReturn(streakWithNullDate)

        val result = habitRepository.completeHabit(testHabitId, today)

        assertTrue("Should handle null date gracefully", result.isSuccess)
        // Should reset streak to 1 when last completion date is null
        verify(mockStreakDao).update(argThat { streak ->
            streak.currentStreak == 1
        })
    }

    @Test
    fun `habit operations handle invalid user ID`() = runTest {
        val invalidUserId = -1L
        whenever(mockHabitDao.getActiveHabitsByUser(invalidUserId)).thenReturn(flowOf(emptyList()))

        val result = habitRepository.getUserHabits(invalidUserId)
        val resultList = mutableListOf<List<Habit>>()
        result.collect { resultList.add(it) }

        assertTrue("Should return empty list for invalid user", resultList.first().isEmpty())
    }

    @Test
    fun `habit completion rate handles zero total days`() = runTest {
        val result = habitRepository.getHabitCompletionRate(testHabitId, 0)

        assertTrue("Should return failure for zero days", result.isFailure)
        assertTrue("Should contain appropriate error message",
            result.exceptionOrNull()?.message?.contains("days must be greater than 0") == true)
    }

    // ========================
    // Helper Methods
    // ========================

    private fun createTestHabit(
        id: Long = testHabitId,
        name: String = "Test Habit",
        userId: Long = testUserId
    ): Habit {
        return Habit(
            id = id,
            userId = userId,
            name = name,
            description = "Test habit description",
            category = HabitCategory.HEALTH,
            targetFrequency = FrequencyType.DAILY,
            reminderTime = "09:00",
            isActive = true,
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}
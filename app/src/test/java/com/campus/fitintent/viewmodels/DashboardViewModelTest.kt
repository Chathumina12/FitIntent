package com.campus.fitintent.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.*
import com.campus.fitintent.utils.PointCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.util.*

/**
 * Unit tests for DashboardViewModel
 * Tests dashboard data loading, progress calculations, and user interactions
 */
@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockHabitRepository: HabitRepository

    @Mock
    private lateinit var mockWorkoutRepository: WorkoutRepository

    @Mock
    private lateinit var mockNutritionRepository: NutritionRepository

    @Mock
    private lateinit var mockProgressRepository: ProgressRepository

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private lateinit var dashboardViewModel: DashboardViewModel

    private val testUserId = 1L

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        dashboardViewModel = DashboardViewModel(
            habitRepository = mockHabitRepository,
            workoutRepository = mockWorkoutRepository,
            nutritionRepository = mockNutritionRepository,
            progressRepository = mockProgressRepository,
            userRepository = mockUserRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ========================
    // Dashboard Data Loading Tests
    // ========================

    @Test
    fun `loadDashboardData loads all components successfully`() = runTest {
        // Arrange
        setupMockRepositories()

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(mockHabitRepository).getUserHabits(testUserId)
        verify(mockWorkoutRepository).getRecentWorkouts(testUserId, 5)
        verify(mockNutritionRepository).getDailyTip()
        verify(mockProgressRepository).getUserLevel(testUserId)
        verify(mockProgressRepository).getTotalPoints(testUserId)
    }

    @Test
    fun `loadDashboardData sets loading state correctly`() = runTest {
        // Arrange
        setupMockRepositories()
        val loadingObserver = mock<Observer<Boolean>>()
        dashboardViewModel.isLoading.observeForever(loadingObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(loadingObserver, atLeast(1)).onChanged(true)
        verify(loadingObserver, atLeast(1)).onChanged(false)
    }

    @Test
    fun `loadDashboardData handles repository errors gracefully`() = runTest {
        // Arrange
        whenever(mockHabitRepository.getUserHabits(testUserId))
            .thenThrow(RuntimeException("Database error"))

        val errorObserver = mock<Observer<String?>>()
        dashboardViewModel.error.observeForever(errorObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(errorObserver).onChanged(argThat { it?.contains("Database error") == true })
    }

    // ========================
    // User Stats Tests
    // ========================

    @Test
    fun `user stats are calculated correctly`() = runTest {
        // Arrange
        val mockHabits = listOf(createTestHabit(), createTestHabit(id = 2L))
        val mockWorkouts = listOf(createTestWorkout())

        whenever(mockHabitRepository.getUserHabits(testUserId)).thenReturn(flowOf(mockHabits))
        whenever(mockWorkoutRepository.getRecentWorkouts(testUserId, 5)).thenReturn(flowOf(mockWorkouts))
        whenever(mockProgressRepository.getTotalPoints(testUserId)).thenReturn(flowOf(2500))
        whenever(mockProgressRepository.getCurrentStreak(testUserId)).thenReturn(flowOf(15))
        whenever(mockProgressRepository.getCompletionRate(testUserId)).thenReturn(flowOf(0.85f))

        val statsObserver = mock<Observer<DashboardStats>>()
        dashboardViewModel.userStats.observeForever(statsObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(statsObserver).onChanged(argThat { stats ->
            stats.totalPoints == 2500 &&
            stats.currentStreak == 15 &&
            stats.completionRate == 85.0f &&
            stats.level == PointCalculator.calculateUserLevel(2500)
        })
    }

    @Test
    fun `today progress is calculated correctly`() = runTest {
        // Arrange
        val completedHabits = 2
        val totalHabits = 3
        val completedWorkouts = 1

        whenever(mockHabitRepository.getTotalHabitsCompletedToday(testUserId))
            .thenReturn(Result.success(completedHabits))
        whenever(mockHabitRepository.getUserHabits(testUserId))
            .thenReturn(flowOf(List(totalHabits) { createTestHabit(id = it.toLong()) }))
        whenever(mockWorkoutRepository.getWorkoutsCompletedToday(testUserId))
            .thenReturn(flowOf(completedWorkouts))

        val progressObserver = mock<Observer<TodayProgress>>()
        dashboardViewModel.todayProgress.observeForever(progressObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(progressObserver).onChanged(argThat { progress ->
            progress.habitsCompleted == completedHabits &&
            progress.totalHabits == totalHabits &&
            progress.workoutsCompleted == completedWorkouts
        })
    }

    // ========================
    // Quick Actions Tests
    // ========================

    @Test
    fun `completeHabit updates habit completion successfully`() = runTest {
        // Arrange
        val habitId = 1L
        whenever(mockHabitRepository.completeHabit(habitId, any())).thenReturn(Result.success(Unit))

        val successObserver = mock<Observer<String?>>()
        dashboardViewModel.successMessage.observeForever(successObserver)

        // Act
        dashboardViewModel.completeHabit(habitId)

        // Assert
        verify(mockHabitRepository).completeHabit(eq(habitId), any())
        verify(successObserver).onChanged(argThat { it?.contains("completed") == true })
    }

    @Test
    fun `completeHabit handles failure gracefully`() = runTest {
        // Arrange
        val habitId = 1L
        val errorMessage = "Failed to complete habit"
        whenever(mockHabitRepository.completeHabit(habitId, any()))
            .thenReturn(Result.failure(RuntimeException(errorMessage)))

        val errorObserver = mock<Observer<String?>>()
        dashboardViewModel.error.observeForever(errorObserver)

        // Act
        dashboardViewModel.completeHabit(habitId)

        // Assert
        verify(errorObserver).onChanged(argThat { it?.contains(errorMessage) == true })
    }

    @Test
    fun `startQuickWorkout initiates workout successfully`() = runTest {
        // Arrange
        val workoutId = 1L
        val workoutSession = createTestWorkoutSession(workoutId)
        whenever(mockWorkoutRepository.startWorkout(workoutId, testUserId))
            .thenReturn(Result.success(workoutSession))

        val quickWorkoutObserver = mock<Observer<WorkoutSession?>>()
        dashboardViewModel.activeWorkout.observeForever(quickWorkoutObserver)

        // Act
        dashboardViewModel.startQuickWorkout(workoutId, testUserId)

        // Assert
        verify(mockWorkoutRepository).startWorkout(workoutId, testUserId)
        verify(quickWorkoutObserver).onChanged(workoutSession)
    }

    // ========================
    // Nutrition Integration Tests
    // ========================

    @Test
    fun `daily nutrition tip is loaded correctly`() = runTest {
        // Arrange
        val dailyTip = createTestNutritionTip()
        whenever(mockNutritionRepository.getDailyTip()).thenReturn(flowOf(dailyTip))

        val tipObserver = mock<Observer<NutritionTip?>>()
        dashboardViewModel.todaysNutritionTip.observeForever(tipObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(tipObserver).onChanged(dailyTip)
    }

    @Test
    fun `markTipAsFavorite updates nutrition preferences`() = runTest {
        // Arrange
        val tipId = 1L
        whenever(mockNutritionRepository.markTipAsFavorite(testUserId, tipId))
            .thenReturn(Result.success(Unit))

        val successObserver = mock<Observer<String?>>()
        dashboardViewModel.successMessage.observeForever(successObserver)

        // Act
        dashboardViewModel.markTipAsFavorite(tipId, testUserId)

        // Assert
        verify(mockNutritionRepository).markTipAsFavorite(testUserId, tipId)
        verify(successObserver).onChanged(argThat { it?.contains("favorite") == true })
    }

    // ========================
    // Rewards and Achievements Tests
    // ========================

    @Test
    fun `recent achievements are loaded correctly`() = runTest {
        // Arrange
        val recentBadges = listOf(createTestBadge(), createTestBadge(id = 2L))
        whenever(mockProgressRepository.getRecentBadges(testUserId, 3))
            .thenReturn(flowOf(recentBadges))

        val badgesObserver = mock<Observer<List<Badge>>>()
        dashboardViewModel.recentAchievements.observeForever(badgesObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(badgesObserver).onChanged(recentBadges)
    }

    @Test
    fun `progress to next level is calculated correctly`() = runTest {
        // Arrange
        val totalPoints = 1750 // Between level 1 (1000) and level 2 (2000)
        val expectedProgress = 75.0f // 750/1000 * 100

        whenever(mockProgressRepository.getTotalPoints(testUserId)).thenReturn(flowOf(totalPoints))

        val levelProgressObserver = mock<Observer<Float>>()
        dashboardViewModel.levelProgress.observeForever(levelProgressObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(levelProgressObserver).onChanged(argThat { progress ->
            Math.abs(progress - expectedProgress) < 1.0f
        })
    }

    // ========================
    // Data Refresh Tests
    // ========================

    @Test
    fun `refreshData reloads all dashboard components`() = runTest {
        // Arrange
        setupMockRepositories()

        // Act
        dashboardViewModel.loadDashboardData(testUserId)
        dashboardViewModel.refreshData()

        // Assert - Should be called twice (initial load + refresh)
        verify(mockHabitRepository, times(2)).getUserHabits(testUserId)
        verify(mockWorkoutRepository, times(2)).getRecentWorkouts(testUserId, 5)
        verify(mockNutritionRepository, times(2)).getDailyTip()
    }

    @Test
    fun `refreshData maintains user ID across calls`() = runTest {
        // Arrange
        setupMockRepositories()
        dashboardViewModel.loadDashboardData(testUserId)

        // Act
        dashboardViewModel.refreshData()

        // Assert
        verify(mockHabitRepository, times(2)).getUserHabits(testUserId)
        verify(mockProgressRepository, times(2)).getTotalPoints(testUserId)
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `dashboard handles empty data gracefully`() = runTest {
        // Arrange
        whenever(mockHabitRepository.getUserHabits(testUserId)).thenReturn(flowOf(emptyList()))
        whenever(mockWorkoutRepository.getRecentWorkouts(testUserId, 5)).thenReturn(flowOf(emptyList()))
        whenever(mockProgressRepository.getTotalPoints(testUserId)).thenReturn(flowOf(0))
        whenever(mockProgressRepository.getCurrentStreak(testUserId)).thenReturn(flowOf(0))
        whenever(mockNutritionRepository.getDailyTip()).thenReturn(flowOf(null))

        val statsObserver = mock<Observer<DashboardStats>>()
        dashboardViewModel.userStats.observeForever(statsObserver)

        // Act
        dashboardViewModel.loadDashboardData(testUserId)

        // Assert
        verify(statsObserver).onChanged(argThat { stats ->
            stats.totalPoints == 0 &&
            stats.currentStreak == 0 &&
            stats.level == 1 // Minimum level
        })
    }

    @Test
    fun `dashboard handles invalid user ID`() = runTest {
        // Arrange
        val invalidUserId = -1L
        whenever(mockHabitRepository.getUserHabits(invalidUserId))
            .thenReturn(flowOf(emptyList()))

        val errorObserver = mock<Observer<String?>>()
        dashboardViewModel.error.observeForever(errorObserver)

        // Act
        dashboardViewModel.loadDashboardData(invalidUserId)

        // Assert - Should handle gracefully without crashing
        assertFalse("Should not crash with invalid user ID",
            dashboardViewModel.isLoading.value ?: true)
    }

    // ========================
    // Helper Methods
    // ========================

    private fun setupMockRepositories() {
        whenever(mockHabitRepository.getUserHabits(testUserId))
            .thenReturn(flowOf(listOf(createTestHabit())))
        whenever(mockWorkoutRepository.getRecentWorkouts(testUserId, 5))
            .thenReturn(flowOf(listOf(createTestWorkout())))
        whenever(mockNutritionRepository.getDailyTip())
            .thenReturn(flowOf(createTestNutritionTip()))
        whenever(mockProgressRepository.getUserLevel(testUserId))
            .thenReturn(flowOf(5))
        whenever(mockProgressRepository.getTotalPoints(testUserId))
            .thenReturn(flowOf(4250))
        whenever(mockProgressRepository.getCurrentStreak(testUserId))
            .thenReturn(flowOf(12))
        whenever(mockProgressRepository.getCompletionRate(testUserId))
            .thenReturn(flowOf(0.78f))
        whenever(mockProgressRepository.getRecentBadges(testUserId, 3))
            .thenReturn(flowOf(emptyList()))
        whenever(mockHabitRepository.getTotalHabitsCompletedToday(testUserId))
            .thenReturn(Result.success(2))
        whenever(mockWorkoutRepository.getWorkoutsCompletedToday(testUserId))
            .thenReturn(flowOf(1))
    }

    private fun createTestHabit(id: Long = 1L): Habit {
        return Habit(
            id = id,
            userId = testUserId,
            name = "Test Habit $id",
            description = "Test habit description",
            category = HabitCategory.HEALTH,
            targetFrequency = FrequencyType.DAILY,
            reminderTime = "09:00",
            isActive = true
        )
    }

    private fun createTestWorkout(id: Long = 1L): Workout {
        return Workout(
            id = id,
            name = "Test Workout $id",
            category = WorkoutCategory.HIIT,
            difficulty = WorkoutDifficulty.INTERMEDIATE,
            durationMinutes = 30,
            description = "Test workout description",
            imageUrl = null,
            exerciseIds = listOf(1L, 2L),
            isActive = true
        )
    }

    private fun createTestWorkoutSession(workoutId: Long): WorkoutSession {
        return WorkoutSession(
            id = 1L,
            userId = testUserId,
            workoutId = workoutId,
            startTime = Date(),
            endTime = null,
            isCompleted = false,
            caloriesBurned = 0,
            notes = null
        )
    }

    private fun createTestNutritionTip(id: Long = 1L): NutritionTip {
        return NutritionTip(
            id = id,
            title = "Test Tip $id",
            description = "Test tip description",
            category = NutritionCategory.HYDRATION,
            benefits = "Test benefits",
            difficulty = TipDifficulty.EASY,
            tags = "test,tip"
        )
    }

    private fun createTestBadge(id: Long = 1L): Badge {
        return Badge(
            id = id,
            name = "Test Badge $id",
            description = "Test badge description",
            type = BadgeType.HABIT,
            rarity = BadgeRarity.COMMON,
            iconResId = "ic_test_badge",
            targetProgress = 10,
            isActive = true
        )
    }
}

/**
 * Data classes for dashboard testing
 */
data class DashboardStats(
    val totalPoints: Int,
    val currentStreak: Int,
    val completionRate: Float,
    val level: Int
)

data class TodayProgress(
    val habitsCompleted: Int,
    val totalHabits: Int,
    val workoutsCompleted: Int
)
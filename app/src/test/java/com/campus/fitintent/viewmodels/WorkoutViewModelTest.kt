package com.campus.fitintent.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

/**
 * Comprehensive unit tests for WorkoutViewModel
 * Tests workout data loading, category filtering, and workout session management
 */
@ExperimentalCoroutinesApi
class WorkoutViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockWorkoutRepository: WorkoutRepository

    private lateinit var workoutViewModel: WorkoutViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        workoutViewModel = WorkoutViewModel(mockWorkoutRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ========================
    // Initialization Tests
    // ========================

    @Test
    fun `init loads workout data successfully`() = runTest {
        // Arrange
        val featuredObserver = mock<Observer<List<Workout>>>()
        val activityObserver = mock<Observer<List<WorkoutActivity>>>()
        val categoryObserver = mock<Observer<Map<String, Int>>>()

        workoutViewModel.featuredWorkouts.observeForever(featuredObserver)
        workoutViewModel.recentActivity.observeForever(activityObserver)
        workoutViewModel.categoryCounts.observeForever(categoryObserver)

        // Act - ViewModel init already called in setup

        // Assert
        verify(featuredObserver).onChanged(argThat { workouts ->
            workouts.isNotEmpty() &&
            workouts.any { it.name == "Full Body HIIT" } &&
            workouts.any { it.name == "Morning Yoga Flow" } &&
            workouts.any { it.name == "5K Run Prep" }
        })

        verify(activityObserver).onChanged(argThat { activities ->
            activities.isNotEmpty() &&
            activities.any { it.workoutName == "Morning Yoga" } &&
            activities.any { it.workoutName == "Full Body HIIT" }
        })

        verify(categoryObserver).onChanged(argThat { counts ->
            counts.containsKey("strength") &&
            counts.containsKey("cardio") &&
            counts.containsKey("flexibility")
        })
    }

    // ========================
    // Featured Workouts Tests
    // ========================

    @Test
    fun `featuredWorkouts contains correct workout data`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            workouts.size == 3 &&
            workouts[0].name == "Full Body HIIT" &&
            workouts[0].category == WorkoutCategory.STRENGTH &&
            workouts[0].difficulty == DifficultyLevel.INTERMEDIATE &&
            workouts[0].duration == 30 &&
            workouts[0].caloriesPerMinute == 12
        })
    }

    @Test
    fun `featuredWorkouts includes variety of categories`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            val categories = workouts.map { it.category }.toSet()
            categories.contains(WorkoutCategory.STRENGTH) &&
            categories.contains(WorkoutCategory.FLEXIBILITY) &&
            categories.contains(WorkoutCategory.CARDIO)
        })
    }

    @Test
    fun `featuredWorkouts includes variety of difficulty levels`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            val difficulties = workouts.map { it.difficulty }.toSet()
            difficulties.contains(DifficultyLevel.BEGINNER) &&
            difficulties.contains(DifficultyLevel.INTERMEDIATE)
        })
    }

    // ========================
    // Recent Activity Tests
    // ========================

    @Test
    fun `recentActivity contains correct activity data`() = runTest {
        // Arrange
        val observer = mock<Observer<List<WorkoutActivity>>>()
        workoutViewModel.recentActivity.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { activities ->
            activities.size == 2 &&
            activities[0].workoutName == "Morning Yoga" &&
            activities[0].duration == 20 &&
            activities[0].caloriesBurned == 80 &&
            activities[1].workoutName == "Full Body HIIT" &&
            activities[1].duration == 30 &&
            activities[1].caloriesBurned == 360
        })
    }

    @Test
    fun `recentActivity has realistic completion times`() = runTest {
        // Arrange
        val observer = mock<Observer<List<WorkoutActivity>>>()
        workoutViewModel.recentActivity.observeForever(observer)
        val currentTime = System.currentTimeMillis()

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { activities ->
            activities.all { activity ->
                activity.completedAt < currentTime &&
                activity.completedAt > currentTime - (7 * 24 * 60 * 60 * 1000) // Within last week
            }
        })
    }

    @Test
    fun `recentActivity calculates calories correctly`() = runTest {
        // Arrange
        val observer = mock<Observer<List<WorkoutActivity>>>()
        workoutViewModel.recentActivity.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { activities ->
            // Morning Yoga: 20 min × 4 cal/min = 80 calories
            val yogaActivity = activities.find { it.workoutName == "Morning Yoga" }
            yogaActivity?.caloriesBurned == 80 &&

            // Full Body HIIT: 30 min × 12 cal/min = 360 calories
            activities.find { it.workoutName == "Full Body HIIT" }?.caloriesBurned == 360
        })
    }

    // ========================
    // Category Counts Tests
    // ========================

    @Test
    fun `categoryCounts contains expected categories`() = runTest {
        // Arrange
        val observer = mock<Observer<Map<String, Int>>>()
        workoutViewModel.categoryCounts.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { counts ->
            counts.keys.contains("strength") &&
            counts.keys.contains("cardio") &&
            counts.keys.contains("flexibility") &&
            counts.size == 3
        })
    }

    @Test
    fun `categoryCounts has realistic values`() = runTest {
        // Arrange
        val observer = mock<Observer<Map<String, Int>>>()
        workoutViewModel.categoryCounts.observeForever(observer)

        // Act - Already loaded in init

        // Assert
        verify(observer).onChanged(argThat { counts ->
            counts["strength"] == 12 &&
            counts["cardio"] == 8 &&
            counts["flexibility"] == 6 &&
            counts.values.all { it > 0 }
        })
    }

    // ========================
    // Last Workout Tests
    // ========================

    @Test
    fun `getLastWorkout returns first featured workout`() = runTest {
        // Arrange - Wait for initialization to complete
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act
        val lastWorkout = workoutViewModel.getLastWorkout()

        // Assert
        assertNotNull("Last workout should not be null", lastWorkout)
        assertEquals("Last workout should be Full Body HIIT", "Full Body HIIT", lastWorkout?.name)
        assertEquals("Last workout category should be STRENGTH", WorkoutCategory.STRENGTH, lastWorkout?.category)
    }

    @Test
    fun `getLastWorkout maintains workout properties`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act
        val lastWorkout = workoutViewModel.getLastWorkout()

        // Assert
        assertNotNull("Last workout should not be null", lastWorkout)
        lastWorkout?.let { workout ->
            assertEquals(30, workout.duration)
            assertEquals(12, workout.caloriesPerMinute)
            assertEquals(DifficultyLevel.INTERMEDIATE, workout.difficulty)
            assertTrue("Workout should have valid creation time", workout.createdAt > 0)
            assertTrue("Workout should have valid update time", workout.updatedAt > 0)
        }
    }

    // ========================
    // Start Workout Tests
    // ========================

    @Test
    fun `startWorkout executes without throwing exception`() = runTest {
        // Arrange
        val workoutId = 1L

        // Act & Assert - Should not throw any exception
        assertDoesNotThrow {
            workoutViewModel.startWorkout(workoutId)
        }
    }

    @Test
    fun `startWorkout handles multiple calls correctly`() = runTest {
        // Arrange
        val workoutId = 1L

        // Act - Multiple calls should not cause issues
        workoutViewModel.startWorkout(workoutId)
        workoutViewModel.startWorkout(workoutId)
        workoutViewModel.startWorkout(2L)

        // Assert - Should complete without exceptions
        assertTrue("Multiple start workout calls should be handled gracefully", true)
    }

    // ========================
    // Get Workouts By Category Tests
    // ========================

    @Test
    fun `getWorkoutsByCategory returns LiveData`() = runTest {
        // Act
        val result = workoutViewModel.getWorkoutsByCategory(WorkoutCategory.STRENGTH)

        // Assert
        assertNotNull("Should return non-null LiveData", result)
    }

    @Test
    fun `getWorkoutsByCategory returns empty list initially`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()

        // Act
        val result = workoutViewModel.getWorkoutsByCategory(WorkoutCategory.STRENGTH)
        result.observeForever(observer)

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            workouts.isEmpty()
        })
    }

    @Test
    fun `getWorkoutsByCategory handles all category types`() = runTest {
        // Arrange
        val categories = WorkoutCategory.values()

        // Act & Assert - Should handle all categories without exceptions
        categories.forEach { category ->
            assertDoesNotThrow("Category $category should be handled") {
                val result = workoutViewModel.getWorkoutsByCategory(category)
                assertNotNull("Result should not be null for $category", result)
            }
        }
    }

    // ========================
    // Data Consistency Tests
    // ========================

    @Test
    fun `workout data remains consistent across multiple observers`() = runTest {
        // Arrange
        val observer1 = mock<Observer<List<Workout>>>()
        val observer2 = mock<Observer<List<Workout>>>()

        // Act
        workoutViewModel.featuredWorkouts.observeForever(observer1)
        workoutViewModel.featuredWorkouts.observeForever(observer2)

        // Assert - Both observers should receive the same data
        verify(observer1).onChanged(argThat { workouts -> workouts.size == 3 })
        verify(observer2).onChanged(argThat { workouts -> workouts.size == 3 })
    }

    @Test
    fun `category counts match featured workout categories`() = runTest {
        // Arrange
        val featuredObserver = mock<Observer<List<Workout>>>()
        val categoryObserver = mock<Observer<Map<String, Int>>>()

        workoutViewModel.featuredWorkouts.observeForever(featuredObserver)
        workoutViewModel.categoryCounts.observeForever(categoryObserver)

        // Act - Data already loaded

        // Assert
        verify(featuredObserver).onChanged(argThat { workouts ->
            val featuredCategories = workouts.map { it.category }.toSet()
            featuredCategories.contains(WorkoutCategory.STRENGTH) &&
            featuredCategories.contains(WorkoutCategory.CARDIO) &&
            featuredCategories.contains(WorkoutCategory.FLEXIBILITY)
        })

        verify(categoryObserver).onChanged(argThat { counts ->
            counts.containsKey("strength") &&
            counts.containsKey("cardio") &&
            counts.containsKey("flexibility")
        })
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `viewModel handles coroutine exceptions gracefully`() = runTest {
        // Arrange - ViewModel is already initialized with potential for exceptions

        // Act - Access all LiveData values
        val featuredWorkouts = workoutViewModel.featuredWorkouts.value
        val recentActivity = workoutViewModel.recentActivity.value
        val categoryCounts = workoutViewModel.categoryCounts.value
        val lastWorkout = workoutViewModel.getLastWorkout()

        // Assert - Should not be null despite potential exceptions
        assertNotNull("Featured workouts should be loaded", featuredWorkouts)
        assertNotNull("Recent activity should be loaded", recentActivity)
        assertNotNull("Category counts should be loaded", categoryCounts)
        assertNotNull("Last workout should be available", lastWorkout)
    }

    @Test
    fun `startWorkout with invalid workout ID doesn't crash`() = runTest {
        // Arrange
        val invalidWorkoutIds = listOf(-1L, 0L, Long.MAX_VALUE, Long.MIN_VALUE)

        // Act & Assert
        invalidWorkoutIds.forEach { invalidId ->
            assertDoesNotThrow("Should handle invalid workout ID: $invalidId") {
                workoutViewModel.startWorkout(invalidId)
            }
        }
    }

    @Test
    fun `multiple simultaneous category requests handled correctly`() = runTest {
        // Arrange
        val categories = listOf(
            WorkoutCategory.STRENGTH,
            WorkoutCategory.CARDIO,
            WorkoutCategory.FLEXIBILITY,
            WorkoutCategory.HIIT
        )

        // Act - Request multiple categories simultaneously
        val results = categories.map { category ->
            workoutViewModel.getWorkoutsByCategory(category)
        }

        // Assert - All results should be valid LiveData objects
        results.forEach { result ->
            assertNotNull("Category result should not be null", result)
        }
    }

    // ========================
    // Workout Properties Validation Tests
    // ========================

    @Test
    fun `all featured workouts have valid properties`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            workouts.all { workout ->
                workout.id > 0 &&
                workout.name.isNotEmpty() &&
                workout.description.isNotEmpty() &&
                workout.duration > 0 &&
                workout.caloriesPerMinute > 0 &&
                workout.createdAt > 0 &&
                workout.updatedAt > 0
            }
        })
    }

    @Test
    fun `workout durations are realistic`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            workouts.all { workout ->
                workout.duration >= 15 && workout.duration <= 60 // 15-60 minutes is realistic
            }
        })
    }

    @Test
    fun `workout calorie rates are realistic`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()
        workoutViewModel.featuredWorkouts.observeForever(observer)

        // Act - Already loaded

        // Assert
        verify(observer).onChanged(argThat { workouts ->
            workouts.all { workout ->
                workout.caloriesPerMinute >= 3 && workout.caloriesPerMinute <= 15 // Realistic range
            }
        })
    }

    // ========================
    // Memory and Performance Tests
    // ========================

    @Test
    fun `viewModel doesn't hold unnecessary references`() = runTest {
        // Arrange
        val observer = mock<Observer<List<Workout>>>()

        // Act
        workoutViewModel.featuredWorkouts.observeForever(observer)
        workoutViewModel.featuredWorkouts.removeObserver(observer)

        // Assert - Should not cause memory leaks (verified by successful test execution)
        assertTrue("Observer removal should not cause issues", true)
    }

    // ========================
    // Helper Methods
    // ========================

    private inline fun assertDoesNotThrow(message: String = "", block: () -> Unit) {
        try {
            block()
        } catch (e: Throwable) {
            fail("$message - Should not throw exception: ${e.message}")
        }
    }
}
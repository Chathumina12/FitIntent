package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.WorkoutRepository
<<<<<<< HEAD
import kotlinx.coroutines.launch

data class WorkoutActivity(
    val workoutName: String,
    val duration: Int,
    val caloriesBurned: Int,
    val completedAt: Long
)
=======
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.launch
import java.util.Date
>>>>>>> 818ab1f (Updated)

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

<<<<<<< HEAD
    private val _featuredWorkouts = MutableLiveData<List<Workout>>()
    val featuredWorkouts: LiveData<List<Workout>> = _featuredWorkouts

    private val _recentActivity = MutableLiveData<List<WorkoutActivity>>()
    val recentActivity: LiveData<List<WorkoutActivity>> = _recentActivity

    private val _categoryCounts = MutableLiveData<Map<String, Int>>()
    val categoryCounts: LiveData<Map<String, Int>> = _categoryCounts

    private var lastWorkout: Workout? = null
=======
    private val _featuredWorkouts = MutableLiveData<Result<List<Workout>>>()
    val featuredWorkouts: LiveData<Result<List<Workout>>> = _featuredWorkouts

    private val _recentActivity = MutableLiveData<Result<List<WorkoutSession>>>()
    val recentActivity: LiveData<Result<List<WorkoutSession>>> = _recentActivity

    private val _categoryCounts = MutableLiveData<Map<WorkoutCategory, Int>>()
    val categoryCounts: LiveData<Map<WorkoutCategory, Int>> = _categoryCounts

    private val _lastWorkout = MutableLiveData<Workout?>()
    val lastWorkout: LiveData<Workout?> = _lastWorkout

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
>>>>>>> 818ab1f (Updated)

    init {
        loadWorkoutData()
    }

<<<<<<< HEAD
    private fun loadWorkoutData() {
        viewModelScope.launch {
            try {
                // Load featured workouts
                loadFeaturedWorkouts()

                // Load recent activity
                loadRecentActivity()

                // Load category counts
                loadCategoryCounts()
            } catch (e: Exception) {
                // Handle error
=======
    fun loadWorkoutData() {
        // Individual load methods will handle their own loading states
    }

    fun loadFeaturedWorkouts() {
        viewModelScope.launch {
            _featuredWorkouts.value = Result.Loading
            try {
                loadFeaturedWorkoutsData()
            } catch (e: Exception) {
                _featuredWorkouts.value = Result.Error("Failed to load featured workouts: ${e.message}")
>>>>>>> 818ab1f (Updated)
            }
        }
    }

<<<<<<< HEAD
    private suspend fun loadFeaturedWorkouts() {
=======
    fun loadRecentActivity() {
        viewModelScope.launch {
            _recentActivity.value = Result.Loading
            try {
                loadRecentActivityData()
            } catch (e: Exception) {
                _recentActivity.value = Result.Error("Failed to load recent activity: ${e.message}")
            }
        }
    }

    fun loadCategoryCounts() {
        viewModelScope.launch {
            try {
                loadCategoryCountsData()
            } catch (e: Exception) {
                _error.value = "Failed to load category counts: ${e.message}"
            }
        }
    }

    fun loadLastWorkout() {
        viewModelScope.launch {
            try {
                loadLastWorkoutData()
            } catch (e: Exception) {
                _error.value = "Failed to load last workout: ${e.message}"
            }
        }
    }

    private suspend fun loadFeaturedWorkoutsData() {
>>>>>>> 818ab1f (Updated)
        // TODO: Load featured workouts from database
        // For now, using mock data
        val mockWorkouts = listOf(
            Workout(
                id = 1,
                name = "Full Body HIIT",
                description = "High-intensity interval training for full body",
                category = WorkoutCategory.STRENGTH,
                difficulty = DifficultyLevel.INTERMEDIATE,
<<<<<<< HEAD
                duration = 30,
                caloriesPerMinute = 12,
                exercises = listOf(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
=======
                durationMinutes = 30,
                caloriesBurned = 360, // 30 minutes * 12 calories/minute
                instructions = "Perform high-intensity intervals with 30 seconds work, 10 seconds rest"
>>>>>>> 818ab1f (Updated)
            ),
            Workout(
                id = 2,
                name = "Morning Yoga Flow",
                description = "Gentle yoga routine to start your day",
                category = WorkoutCategory.FLEXIBILITY,
                difficulty = DifficultyLevel.BEGINNER,
<<<<<<< HEAD
                duration = 20,
                caloriesPerMinute = 4,
                exercises = listOf(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
=======
                durationMinutes = 20,
                caloriesBurned = 80, // 20 minutes * 4 calories/minute
                instructions = "Flow through gentle yoga poses focusing on breath and movement"
>>>>>>> 818ab1f (Updated)
            ),
            Workout(
                id = 3,
                name = "5K Run Prep",
                description = "Cardio workout to prepare for 5K runs",
                category = WorkoutCategory.CARDIO,
                difficulty = DifficultyLevel.INTERMEDIATE,
<<<<<<< HEAD
                duration = 35,
                caloriesPerMinute = 10,
                exercises = listOf(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
        _featuredWorkouts.value = mockWorkouts
        lastWorkout = mockWorkouts.firstOrNull()
    }

    private suspend fun loadRecentActivity() {
        // TODO: Load recent workout activity from database
        // For now, using mock data
        val mockActivity = listOf(
            WorkoutActivity(
                workoutName = "Morning Yoga",
                duration = 20,
                caloriesBurned = 80,
                completedAt = System.currentTimeMillis() - 86400000 // Yesterday
            ),
            WorkoutActivity(
                workoutName = "Full Body HIIT",
                duration = 30,
                caloriesBurned = 360,
                completedAt = System.currentTimeMillis() - 172800000 // 2 days ago
            )
        )
        _recentActivity.value = mockActivity
    }

    private suspend fun loadCategoryCounts() {
        // TODO: Load category counts from database
        // For now, using mock data
        val counts = mapOf(
            "strength" to 12,
            "cardio" to 8,
            "flexibility" to 6
=======
                durationMinutes = 35,
                caloriesBurned = 350, // 35 minutes * 10 calories/minute
                instructions = "Interval running with varying intensities to build endurance"
            )
        )
        _featuredWorkouts.value = Result.Success(mockWorkouts)
        _lastWorkout.value = mockWorkouts.firstOrNull()
    }

    private suspend fun loadRecentActivityData() {
        // TODO: Load recent workout activity from database
        // For now, using mock data with proper WorkoutSession entity
        val mockActivity = listOf(
            WorkoutSession(
                id = 1,
                userId = 1L, // Mock user ID
                workoutId = 2L, // Morning Yoga
                startTime = Date(System.currentTimeMillis() - 86400000 - 1200000), // Yesterday - 20 minutes
                endTime = Date(System.currentTimeMillis() - 86400000), // Yesterday
                durationMinutes = 20,
                caloriesBurned = 80,
                isCompleted = true,
                completionPercentage = 100f
            ),
            WorkoutSession(
                id = 2,
                userId = 1L, // Mock user ID
                workoutId = 1L, // Full Body HIIT
                startTime = Date(System.currentTimeMillis() - 172800000 - 1800000), // 2 days ago - 30 minutes
                endTime = Date(System.currentTimeMillis() - 172800000), // 2 days ago
                durationMinutes = 30,
                caloriesBurned = 360,
                isCompleted = true,
                completionPercentage = 100f
            )
        )
        _recentActivity.value = Result.Success(mockActivity)
    }

    private suspend fun loadCategoryCountsData() {
        // TODO: Load category counts from database
        // For now, using mock data
        val counts = mapOf(
            WorkoutCategory.STRENGTH to 12,
            WorkoutCategory.CARDIO to 8,
            WorkoutCategory.FLEXIBILITY to 6,
            WorkoutCategory.HIIT to 4
>>>>>>> 818ab1f (Updated)
        )
        _categoryCounts.value = counts
    }

<<<<<<< HEAD
    fun getLastWorkout(): Workout? {
        return lastWorkout
    }

    fun startWorkout(workoutId: Long) {
=======
    private suspend fun loadLastWorkoutData() {
        // TODO: Load last completed workout from database
        // For now, using mock data
        val mockLastWorkout = Workout(
            id = 1,
            name = "Full Body HIIT",
            description = "High-intensity interval training for full body",
            category = WorkoutCategory.HIIT,
            difficulty = DifficultyLevel.INTERMEDIATE,
            durationMinutes = 30,
            caloriesBurned = 360,
            instructions = "Perform high-intensity intervals with 30 seconds work, 10 seconds rest"
        )
        _lastWorkout.value = mockLastWorkout
    }

    fun startWorkoutSession(workoutId: Long) {
>>>>>>> 818ab1f (Updated)
        viewModelScope.launch {
            try {
                // TODO: Start workout session and navigate to timer
                // For now, just log the action
            } catch (e: Exception) {
<<<<<<< HEAD
                // Handle error
=======
                _error.value = "Failed to start workout: ${e.message}"
>>>>>>> 818ab1f (Updated)
            }
        }
    }

<<<<<<< HEAD
    fun getWorkoutsByCategory(category: WorkoutCategory): LiveData<List<Workout>> {
        val result = MutableLiveData<List<Workout>>()
        viewModelScope.launch {
            try {
                // TODO: Load workouts by category from database
                result.value = emptyList()
            } catch (e: Exception) {
                // Handle error
                result.value = emptyList()
=======
    fun getWorkoutsByCategory(category: WorkoutCategory): LiveData<Result<List<Workout>>> {
        val result = MutableLiveData<Result<List<Workout>>>()
        viewModelScope.launch {
            result.value = Result.Loading
            try {
                // TODO: Load workouts by category from database
                result.value = Result.Success(emptyList())
            } catch (e: Exception) {
                result.value = Result.Error("Failed to load workouts: ${e.message}")
>>>>>>> 818ab1f (Updated)
            }
        }
        return result
    }
<<<<<<< HEAD
=======

    fun clearError() {
        _error.value = null
    }
>>>>>>> 818ab1f (Updated)
}
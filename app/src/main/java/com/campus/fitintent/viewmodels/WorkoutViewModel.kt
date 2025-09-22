package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.WorkoutRepository
import kotlinx.coroutines.launch

data class WorkoutActivity(
    val workoutName: String,
    val duration: Int,
    val caloriesBurned: Int,
    val completedAt: Long
)

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _featuredWorkouts = MutableLiveData<List<Workout>>()
    val featuredWorkouts: LiveData<List<Workout>> = _featuredWorkouts

    private val _recentActivity = MutableLiveData<List<WorkoutActivity>>()
    val recentActivity: LiveData<List<WorkoutActivity>> = _recentActivity

    private val _categoryCounts = MutableLiveData<Map<String, Int>>()
    val categoryCounts: LiveData<Map<String, Int>> = _categoryCounts

    private var lastWorkout: Workout? = null

    init {
        loadWorkoutData()
    }

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
            }
        }
    }

    private suspend fun loadFeaturedWorkouts() {
        // TODO: Load featured workouts from database
        // For now, using mock data
        val mockWorkouts = listOf(
            Workout(
                id = 1,
                name = "Full Body HIIT",
                description = "High-intensity interval training for full body",
                category = WorkoutCategory.STRENGTH,
                difficulty = DifficultyLevel.INTERMEDIATE,
                duration = 30,
                caloriesPerMinute = 12,
                exercises = listOf(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Workout(
                id = 2,
                name = "Morning Yoga Flow",
                description = "Gentle yoga routine to start your day",
                category = WorkoutCategory.FLEXIBILITY,
                difficulty = DifficultyLevel.BEGINNER,
                duration = 20,
                caloriesPerMinute = 4,
                exercises = listOf(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Workout(
                id = 3,
                name = "5K Run Prep",
                description = "Cardio workout to prepare for 5K runs",
                category = WorkoutCategory.CARDIO,
                difficulty = DifficultyLevel.INTERMEDIATE,
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
        )
        _categoryCounts.value = counts
    }

    fun getLastWorkout(): Workout? {
        return lastWorkout
    }

    fun startWorkout(workoutId: Long) {
        viewModelScope.launch {
            try {
                // TODO: Start workout session and navigate to timer
                // For now, just log the action
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getWorkoutsByCategory(category: WorkoutCategory): LiveData<List<Workout>> {
        val result = MutableLiveData<List<Workout>>()
        viewModelScope.launch {
            try {
                // TODO: Load workouts by category from database
                result.value = emptyList()
            } catch (e: Exception) {
                // Handle error
                result.value = emptyList()
            }
        }
        return result
    }
}
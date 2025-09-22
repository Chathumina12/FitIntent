package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.UserRepository
import kotlinx.coroutines.launch

data class DailyProgress(
    val progressPercentage: Int,
    val goalText: String,
    val completedTasks: Int,
    val totalTasks: Int
)

class DashboardViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _dailyProgress = MutableLiveData<DailyProgress>()
    val dailyProgress: LiveData<DailyProgress> = _dailyProgress

    private val _currentStreak = MutableLiveData(0)
    val currentStreak: LiveData<Int> = _currentStreak

    private val _totalPoints = MutableLiveData(0)
    val totalPoints: LiveData<Int> = _totalPoints

    private val _todaysHabits = MutableLiveData<List<Habit>>()
    val todaysHabits: LiveData<List<Habit>> = _todaysHabits

    private val _recentAchievements = MutableLiveData<List<Badge>>()
    val recentAchievements: LiveData<List<Badge>> = _recentAchievements

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // Load current user
                val user = userRepository.getCurrentUser()
                _currentUser.value = user

                user?.let {
                    // Load user's points
                    _totalPoints.value = it.totalPoints

                    // Load current streak
                    loadCurrentStreak(it.id)

                    // Load daily progress
                    loadDailyProgress(it.id)

                    // Load today's habits
                    loadTodaysHabits(it.id)

                    // Load recent achievements
                    loadRecentAchievements(it.id)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun loadCurrentStreak(userId: Long) {
        // TODO: Calculate current streak from database
        // For now, using mock data
        _currentStreak.value = 7
    }

    private suspend fun loadDailyProgress(userId: Long) {
        // TODO: Calculate daily progress from database
        // For now, using mock data
        val completedTasks = 3
        val totalTasks = 5
        val percentage = (completedTasks * 100) / totalTasks
        val remaining = totalTasks - completedTasks

        _dailyProgress.value = DailyProgress(
            progressPercentage = percentage,
            goalText = if (remaining > 0) "Complete $remaining more tasks" else "All tasks completed!",
            completedTasks = completedTasks,
            totalTasks = totalTasks
        )
    }

    private suspend fun loadTodaysHabits(userId: Long) {
        // TODO: Load today's habits from database
        // For now, using mock data
        val mockHabits = listOf(
            Habit(
                id = 1,
                userId = userId,
                name = "Morning Workout",
                description = "30 minutes cardio",
                category = HabitCategory.EXERCISE,
                targetDays = 21,
                completedDays = 7,
                isActive = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Habit(
                id = 2,
                userId = userId,
                name = "Drink Water",
                description = "8 glasses per day",
                category = HabitCategory.HYDRATION,
                targetDays = 21,
                completedDays = 5,
                isActive = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Habit(
                id = 3,
                userId = userId,
                name = "Healthy Breakfast",
                description = "High protein meal",
                category = HabitCategory.NUTRITION,
                targetDays = 21,
                completedDays = 10,
                isActive = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
        _todaysHabits.value = mockHabits
    }

    private suspend fun loadRecentAchievements(userId: Long) {
        // TODO: Load recent badges from database
        // For now, using mock data
        val mockBadges = listOf(
            Badge(
                id = 1,
                name = "Week Warrior",
                description = "7 day streak",
                iconResource = "ic_badge_streak",
                pointsRequired = 100,
                category = BadgeCategory.STREAK,
                unlockedAt = System.currentTimeMillis()
            ),
            Badge(
                id = 2,
                name = "Early Bird",
                description = "5 morning workouts",
                iconResource = "ic_badge_morning",
                pointsRequired = 50,
                category = BadgeCategory.WORKOUT,
                unlockedAt = System.currentTimeMillis()
            )
        )
        _recentAchievements.value = mockBadges
    }

    fun refreshDashboard() {
        loadDashboardData()
    }
}
package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.map
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.Result
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

    // User name property for UI binding
    val name: LiveData<String> = currentUser.map { user ->
        user?.fullName?.split(" ")?.firstOrNull() ?: "User"
    }

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
                val userResult = userRepository.getCurrentUser()

                when (userResult) {
                    is Result.Success -> {
                        val user = userResult.data
                        _currentUser.value = user

                        // Load user's points (placeholder - points system not implemented yet)
                        _totalPoints.value = 0

                        // Load current streak
                        loadCurrentStreak(user.id)

                        // Load daily progress
                        loadDailyProgress(user.id)

                        // Load today's habits
                        loadTodaysHabits(user.id)

                        // Load recent achievements
                        loadRecentAchievements(user.id)
                    }
                    is Result.Error -> {
                        // Handle error - user not found or not logged in
                        _currentUser.value = null
                        _totalPoints.value = 0
                    }
                    else -> {
                        // Handle loading state if needed
                        _currentUser.value = null
                        _totalPoints.value = 0
                    }
                }
            } catch (e: Exception) {
                // Handle error
                _currentUser.value = null
                _totalPoints.value = 0
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
                currentStreak = 7,
                isActive = true,
                createdAt = java.util.Date(System.currentTimeMillis()),
                updatedAt = java.util.Date(System.currentTimeMillis())
            ),
            Habit(
                id = 2,
                userId = userId,
                name = "Drink Water",
                description = "8 glasses per day",
                category = HabitCategory.HYDRATION,
                targetDays = 21,
                completedDays = 5,
                currentStreak = 3,
                isActive = true,
                createdAt = java.util.Date(System.currentTimeMillis()),
                updatedAt = java.util.Date(System.currentTimeMillis())
            ),
            Habit(
                id = 3,
                userId = userId,
                name = "Healthy Breakfast",
                description = "High protein meal",
                category = HabitCategory.NUTRITION,
                targetDays = 21,
                completedDays = 10,
                currentStreak = 5,
                isActive = true,
                createdAt = java.util.Date(System.currentTimeMillis()),
                updatedAt = java.util.Date(System.currentTimeMillis())
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
                type = BadgeType.STREAK,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_streak",
                targetProgress = 7,
                createdAt = java.util.Date(System.currentTimeMillis()),
                updatedAt = java.util.Date(System.currentTimeMillis())
            ),
            Badge(
                id = 2,
                name = "Early Bird",
                description = "5 morning workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_morning",
                targetProgress = 5,
                createdAt = java.util.Date(System.currentTimeMillis()),
                updatedAt = java.util.Date(System.currentTimeMillis())
            )
        )
        _recentAchievements.value = mockBadges
    }

    fun refreshDashboard() {
        loadDashboardData()
    }
}
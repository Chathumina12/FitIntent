package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.fragments.UserStats
import com.campus.fitintent.models.Badge // Full Badge model from Badge.kt
import com.campus.fitintent.models.StreakType
import com.campus.fitintent.repository.ProgressRepository
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.BadgeManager
import com.campus.fitintent.utils.PointCalculator
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.launch

/**
 * ViewModel for Rewards Fragment
 * Handles badge progress, user statistics, and reward-related operations
 */
class RewardsViewModel(
    private val progressRepository: ProgressRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val badgeManager = BadgeManager(progressRepository)

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // User statistics (level, points, etc.)
    private val _userStats = MutableLiveData<UserStats>()
    val userStats: LiveData<UserStats> = _userStats

    // Recent achievements (badges earned recently)
    private val _recentAchievements = MutableLiveData<List<BadgeDisplayItem>>()
    val recentAchievements: LiveData<List<BadgeDisplayItem>> = _recentAchievements

    // Upcoming badges (badges close to being unlocked)
    private val _upcomingBadges = MutableLiveData<List<BadgeDisplayItem>>()
    val upcomingBadges: LiveData<List<BadgeDisplayItem>> = _upcomingBadges

    // All user badges (for full badge collection view)
    private val _allUserBadges = MutableLiveData<List<BadgeDisplayItem>>()
    val allUserBadges: LiveData<List<BadgeDisplayItem>> = _allUserBadges

    // Error handling
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Load user statistics (level, points, streak, etc.)
     */
    fun loadUserStats(userId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Get progress statistics (simplified calculation)
                val totalPoints = 1250 // Mock data - should be calculated from repositories
                val level = PointCalculator.calculateUserLevel(totalPoints)

                // Get current streak from the most active streak type
                val currentStreak = getCurrentStreakDays(userId)

                // Mock completion rate
                val completionRate = 85f

                val userStats = UserStats(
                    level = level,
                    totalPoints = totalPoints,
                    badgesEarned = 8, // Mock data
                    currentStreak = currentStreak,
                    completionRate = completionRate
                )

                _userStats.value = userStats
            } catch (e: Exception) {
                _error.value = "Failed to load user stats: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Load recent achievements (badges earned in the last 30 days)
     */
    fun loadRecentAchievements(userId: Long) {
        viewModelScope.launch {
            try {
                val badgeStats = badgeManager.getBadgeStatistics(userId)
                val recentBadges = badgeStats.recentlyUnlocked

                val displayItems = recentBadges.map { badge ->
                    BadgeDisplayItem(
                        badge = badge,
                        progress = 100f, // Recent achievements are fully unlocked
                        isUnlocked = true,
                        pointValue = PointCalculator.calculateBadgePoints(badge),
                        currentProgress = badge.targetProgress,
                        targetProgress = badge.targetProgress,
                        unlockedAt = java.util.Date() // Mock unlock date - should come from UserBadge
                    )
                }

                _recentAchievements.value = displayItems
            } catch (e: Exception) {
                _error.value = "Failed to load recent achievements: ${e.message}"
            }
        }
    }

    /**
     * Load upcoming badges (badges that are 50% or more progress but not yet unlocked)
     */
    fun loadUpcomingBadges(userId: Long) {
        viewModelScope.launch {
            try {
                val upcomingBadges = badgeManager.getUpcomingBadges(userId, limit = 10)

                val displayItems = upcomingBadges.map { badgeProgress ->
                    BadgeDisplayItem(
                        badge = badgeProgress.badge,
                        progress = badgeProgress.progressPercentage,
                        isUnlocked = badgeProgress.isUnlocked,
                        pointValue = PointCalculator.calculateBadgePoints(badgeProgress.badge),
                        currentProgress = badgeProgress.currentProgress,
                        targetProgress = badgeProgress.targetProgress,
                        unlockedAt = if (badgeProgress.isUnlocked) java.util.Date() else null
                    )
                }

                _upcomingBadges.value = displayItems.sortedByDescending { it.progress }
            } catch (e: Exception) {
                _error.value = "Failed to load upcoming badges: ${e.message}"
            }
        }
    }

    /**
     * Load all user badges for comprehensive view
     */
    fun loadAllUserBadges(userId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val allBadges = badgeManager.getAllBadges()
                val upcomingBadges = badgeManager.getUpcomingBadges(userId, limit = 100) // Get all badges with progress
                val badgeStats = badgeManager.getBadgeStatistics(userId)

                val displayItems = allBadges.map { badge ->
                    val badgeProgress = upcomingBadges.find { it.badge.id == badge.id }
                    val isUnlocked = badgeStats.recentlyUnlocked.any { it.id == badge.id } ||
                                   (badgeProgress?.isUnlocked == true)

                    BadgeDisplayItem(
                        badge = badge,
                        progress = badgeProgress?.progressPercentage ?: (if (isUnlocked) 100f else 0f),
                        isUnlocked = isUnlocked,
                        pointValue = PointCalculator.calculateBadgePoints(badge),
                        currentProgress = badgeProgress?.currentProgress ?: (if (isUnlocked) badge.targetProgress else 0),
                        targetProgress = badge.targetProgress,
                        unlockedAt = if (isUnlocked) java.util.Date() else null
                    )
                }

                _allUserBadges.value = displayItems.sortedWith(
                    compareByDescending<BadgeDisplayItem> { it.isUnlocked }
                        .thenByDescending { it.progress }
                        .thenBy { it.badge.rarity.ordinal }
                )
            } catch (e: Exception) {
                _error.value = "Failed to load all badges: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Refresh all rewards data
     */
    fun refreshAll(userId: Long) {
        loadUserStats(userId)
        loadRecentAchievements(userId)
        loadUpcomingBadges(userId)
    }

    /**
     * Clear error state
     */
    fun clearError() {
        _error.value = null
    }

    // Private helper methods

    private suspend fun getCurrentStreakDays(userId: Long): Int {
        return try {
            // Get the most active streak (workout streak is typically most important)
            val workoutStreak = progressRepository.updateStreak(userId, StreakType.WORKOUT, false)
            when (workoutStreak) {
                is Result.Success -> workoutStreak.data.currentStreak
                else -> 0
            }
        } catch (e: Exception) {
            0
        }
    }
}

/**
 * Data class for badge display in UI
 * Uses the Badge model from BadgeManager (Badge.kt) which has complete badge information
 */
data class BadgeDisplayItem(
    val badge: Badge, // Badge from Badge.kt with full details
    val progress: Float,
    val isUnlocked: Boolean,
    val pointValue: Int,
    val currentProgress: Int = 0,
    val targetProgress: Int = 0,
    val unlockedAt: java.util.Date? = null
)
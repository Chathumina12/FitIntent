package com.campus.fitintent.utils

import com.campus.fitintent.models.*
import com.campus.fitintent.repository.ProgressRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

/**
 * Manages badge progress tracking, unlock conditions, and badge achievements
 * Handles all badge-related logic and notifications
 */
class BadgeManager(
    private val progressRepository: ProgressRepository
) {

    /**
     * Check and update badge progress based on user activities
     */
    suspend fun checkBadgeProgress(userId: Long, activityType: BadgeActivity, value: Int = 1) {
        val userBadgeRecords = progressRepository.getUserBadgeRecords(userId).firstOrNull() ?: return
        val availableBadges = getAllBadges()

        for (badge in availableBadges) {
            val userBadge = userBadgeRecords.find { it.badgeId == badge.id }

            if (userBadge?.isUnlocked == true) continue // Already unlocked

            if (isBadgeEligible(badge, activityType)) {
                updateBadgeProgress(userId, badge, activityType, value)
            }
        }
    }

    /**
     * Update progress for a specific badge
     */
    private suspend fun updateBadgeProgress(
        userId: Long,
        badge: Badge,
        activityType: BadgeActivity,
        value: Int
    ) {
        val currentProgress = progressRepository.getBadgeProgress(userId, badge.id).firstOrNull() ?: 0
        val newProgress = currentProgress + value
        val targetProgress = badge.targetProgress

        // Update progress in database
        progressRepository.updateBadgeProgress(userId, badge.id, newProgress)

        // Check if badge should be unlocked
        if (newProgress >= targetProgress) {
            unlockBadge(userId, badge)
        }
    }

    /**
     * Unlock a badge for the user
     */
    private suspend fun unlockBadge(userId: Long, badge: Badge) {
        // Mark badge as unlocked
        progressRepository.unlockUserBadge(userId, badge.id, Date())

        // Award points for badge unlock
        val badgePoints = PointCalculator.calculateBadgePoints(badge)
        progressRepository.addUserPoints(userId, badgePoints, "Badge unlocked: ${badge.name}")

        // TODO: Send notification about badge unlock
        // NotificationManager.sendBadgeUnlockNotification(badge)
    }

    /**
     * Check if a badge is eligible for progress update based on activity type
     */
    private fun isBadgeEligible(badge: Badge, activityType: BadgeActivity): Boolean {
        return when (badge.type) {
            BadgeType.HABIT -> activityType == BadgeActivity.HABIT_COMPLETED
            BadgeType.WORKOUT -> activityType in listOf(
                BadgeActivity.WORKOUT_COMPLETED,
                BadgeActivity.STRENGTH_WORKOUT,
                BadgeActivity.CARDIO_WORKOUT,
                BadgeActivity.HIIT_WORKOUT,
                BadgeActivity.FLEXIBILITY_WORKOUT
            )
            BadgeType.STREAK -> activityType == BadgeActivity.STREAK_MILESTONE
            BadgeType.NUTRITION -> activityType == BadgeActivity.NUTRITION_FAVORITE
            BadgeType.TIME -> activityType == BadgeActivity.EARLY_BIRD
            BadgeType.CONSISTENCY -> activityType in listOf(
                BadgeActivity.DAILY_GOAL_ACHIEVED,
                BadgeActivity.WEEKLY_GOAL_ACHIEVED
            )
            BadgeType.SPECIAL -> true // Special badges can be triggered by any activity

            // Specific badge achievements
            BadgeType.GOAL_GETTER -> activityType == BadgeActivity.DAILY_GOAL_ACHIEVED
            BadgeType.CALORIE_CRUSHER -> activityType == BadgeActivity.DAILY_GOAL_ACHIEVED
            BadgeType.STEP_MASTER -> activityType == BadgeActivity.DAILY_GOAL_ACHIEVED
            BadgeType.WATER_CHAMPION -> activityType == BadgeActivity.DAILY_GOAL_ACHIEVED
            BadgeType.CONSISTENCY_KING -> activityType == BadgeActivity.STREAK_MILESTONE
            BadgeType.MONTH_MASTER -> activityType == BadgeActivity.STREAK_MILESTONE
            BadgeType.WEEK_WARRIOR -> activityType == BadgeActivity.STREAK_MILESTONE
            BadgeType.HABIT_MASTER -> activityType == BadgeActivity.HABIT_COMPLETED
            BadgeType.HABIT_BUILDER -> activityType == BadgeActivity.HABIT_COMPLETED
            BadgeType.HABIT_STARTER -> activityType == BadgeActivity.HABIT_COMPLETED
        }
    }

    /**
     * Get all available badges with their unlock conditions
     */
    fun getAllBadges(): List<Badge> {
        return listOf(
            // Habit Formation Badges
            Badge(
                id = 1,
                name = "First Step",
                description = "Complete your first habit",
                type = BadgeType.HABIT,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_first_step",
                targetProgress = 1,
                isActive = true
            ),
            Badge(
                id = 2,
                name = "Consistency Builder",
                description = "Complete 10 habits",
                type = BadgeType.HABIT,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_consistency",
                targetProgress = 10,
                isActive = true
            ),
            Badge(
                id = 3,
                name = "Habit Former",
                description = "Complete 50 habits",
                type = BadgeType.HABIT,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_habit_former",
                targetProgress = 50,
                isActive = true
            ),
            Badge(
                id = 4,
                name = "Lifestyle Master",
                description = "Complete 200 habits",
                type = BadgeType.HABIT,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_lifestyle_master",
                targetProgress = 200,
                isActive = true
            ),

            // Workout Badges
            Badge(
                id = 5,
                name = "First Sweat",
                description = "Complete your first workout",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_first_sweat",
                targetProgress = 1,
                isActive = true
            ),
            Badge(
                id = 6,
                name = "Fitness Enthusiast",
                description = "Complete 25 workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_fitness_enthusiast",
                targetProgress = 25,
                isActive = true
            ),
            Badge(
                id = 7,
                name = "Strength Master",
                description = "Complete 50 strength workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_strength_master",
                targetProgress = 50,
                isActive = true
            ),
            Badge(
                id = 8,
                name = "Cardio King",
                description = "Complete 50 cardio workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_cardio_king",
                targetProgress = 50,
                isActive = true
            ),
            Badge(
                id = 9,
                name = "Flexibility Pro",
                description = "Complete 30 flexibility workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_flexibility_pro",
                targetProgress = 30,
                isActive = true
            ),
            Badge(
                id = 10,
                name = "HIIT Hero",
                description = "Complete 40 HIIT workouts",
                type = BadgeType.WORKOUT,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_hiit_hero",
                targetProgress = 40,
                isActive = true
            ),

            // Streak Badges
            Badge(
                id = 11,
                name = "Getting Started",
                description = "Maintain a 3-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_getting_started",
                targetProgress = 3,
                isActive = true
            ),
            Badge(
                id = 12,
                name = "Week Warrior",
                description = "Maintain a 7-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_week_warrior",
                targetProgress = 7,
                isActive = true
            ),
            Badge(
                id = 13,
                name = "Habit Former",
                description = "Maintain a 21-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_habit_former_streak",
                targetProgress = 21,
                isActive = true
            ),
            Badge(
                id = 14,
                name = "Consistency King",
                description = "Maintain a 90-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_consistency_king",
                targetProgress = 90,
                isActive = true
            ),
            Badge(
                id = 15,
                name = "Legend",
                description = "Maintain a 365-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.LEGENDARY,
                iconResId = "ic_badge_legend",
                targetProgress = 365,
                isActive = true
            ),

            // Time-based Badges
            Badge(
                id = 16,
                name = "Early Bird",
                description = "Complete 10 morning workouts (before 9 AM)",
                type = BadgeType.TIME,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_early_bird",
                targetProgress = 10,
                isActive = true
            ),
            Badge(
                id = 17,
                name = "Night Owl",
                description = "Complete 10 evening workouts (after 8 PM)",
                type = BadgeType.TIME,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_night_owl",
                targetProgress = 10,
                isActive = true
            ),

            // Nutrition Badges
            Badge(
                id = 18,
                name = "Nutrition Explorer",
                description = "Favorite 5 nutrition tips",
                type = BadgeType.NUTRITION,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_nutrition_explorer",
                targetProgress = 5,
                isActive = true
            ),
            Badge(
                id = 19,
                name = "Hydration Hero",
                description = "Favorite 3 hydration tips",
                type = BadgeType.NUTRITION,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_badge_hydration_hero",
                targetProgress = 3,
                isActive = true
            ),

            // Consistency Badges
            Badge(
                id = 20,
                name = "Goal Crusher",
                description = "Achieve 30 daily goals",
                type = BadgeType.CONSISTENCY,
                rarity = BadgeRarity.RARE,
                iconResId = "ic_badge_goal_crusher",
                targetProgress = 30,
                isActive = true
            ),
            Badge(
                id = 21,
                name = "Perfect Week",
                description = "Complete all goals for a full week",
                type = BadgeType.CONSISTENCY,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_perfect_week",
                targetProgress = 7,
                isActive = true
            ),

            // Special Achievement Badges
            Badge(
                id = 22,
                name = "Overachiever",
                description = "Exceed daily goals by 50% for 5 consecutive days",
                type = BadgeType.SPECIAL,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_badge_overachiever",
                targetProgress = 5,
                isActive = true
            ),
            Badge(
                id = 23,
                name = "All-Rounder",
                description = "Complete workouts in all categories",
                type = BadgeType.SPECIAL,
                rarity = BadgeRarity.LEGENDARY,
                iconResId = "ic_badge_all_rounder",
                targetProgress = 6, // Number of workout categories
                isActive = true
            ),
            Badge(
                id = 24,
                name = "Transformer",
                description = "Use FitIntent for 100 consecutive days",
                type = BadgeType.SPECIAL,
                rarity = BadgeRarity.LEGENDARY,
                iconResId = "ic_badge_transformer",
                targetProgress = 100,
                isActive = true
            )
        )
    }

    /**
     * Get badges for a specific category/type
     */
    fun getBadgesByType(type: BadgeType): List<Badge> {
        return getAllBadges().filter { it.type == type }
    }

    /**
     * Get badges by rarity
     */
    fun getBadgesByRarity(rarity: BadgeRarity): List<Badge> {
        return getAllBadges().filter { it.rarity == rarity }
    }

    /**
     * Calculate badge completion percentage for user
     */
    suspend fun calculateBadgeCompletionPercentage(userId: Long): Float {
        val allBadges = getAllBadges()
        val userBadgeRecords = progressRepository.getUserBadgeRecords(userId).firstOrNull() ?: emptyList()
        val unlockedCount = userBadgeRecords.count { it.isUnlocked }

        return if (allBadges.isNotEmpty()) {
            unlockedCount.toFloat() / allBadges.size
        } else {
            0f
        }
    }

    /**
     * Get user's badge statistics
     */
    suspend fun getBadgeStatistics(userId: Long): BadgeStatistics {
        val allBadges = getAllBadges()
        val userBadgeRecords = progressRepository.getUserBadgeRecords(userId).firstOrNull() ?: emptyList()

        val unlockedBadgeRecords = userBadgeRecords.filter { it.isUnlocked }
        val totalBadges = allBadges.size
        val completionPercentage = if (totalBadges > 0) {
            (unlockedBadgeRecords.size.toFloat() / totalBadges) * 100
        } else {
            0f
        }

        val badgesByRarity = unlockedBadgeRecords.groupBy { userBadge ->
            allBadges.find { it.id == userBadge.badgeId }?.rarity ?: BadgeRarity.COMMON
        }

        return BadgeStatistics(
            totalBadges = totalBadges,
            unlockedBadges = unlockedBadgeRecords.size,
            completionPercentage = completionPercentage,
            commonBadges = badgesByRarity[BadgeRarity.COMMON]?.size ?: 0,
            rareBadges = badgesByRarity[BadgeRarity.RARE]?.size ?: 0,
            epicBadges = badgesByRarity[BadgeRarity.EPIC]?.size ?: 0,
            legendaryBadges = badgesByRarity[BadgeRarity.LEGENDARY]?.size ?: 0,
            recentlyUnlocked = unlockedBadgeRecords
                .sortedByDescending { it.unlockedAt }
                .take(3)
                .mapNotNull { userBadge ->
                    allBadges.find { it.id == userBadge.badgeId }
                }
        )
    }

    /**
     * Get next badges user is close to unlocking
     */
    suspend fun getUpcomingBadges(userId: Long, limit: Int = 5): List<BadgeProgress> {
        val allBadges = getAllBadges()
        val userBadgeRecords = progressRepository.getUserBadgeRecords(userId).firstOrNull() ?: emptyList()

        val unlockedBadgeIds = userBadgeRecords.filter { it.isUnlocked }.map { it.badgeId }.toSet()
        val availableBadges = allBadges.filter { it.id !in unlockedBadgeIds }

        return availableBadges.map { badge ->
            val currentProgress = progressRepository.getBadgeProgress(userId, badge.id).firstOrNull() ?: 0
            val progressPercentage = (currentProgress.toFloat() / badge.targetProgress) * 100

            BadgeProgress(
                badge = badge,
                currentProgress = currentProgress,
                targetProgress = badge.targetProgress,
                progressPercentage = progressPercentage,
                isUnlocked = false
            )
        }.sortedByDescending { it.progressPercentage }
         .take(limit)
    }

    /**
     * Data class for badge statistics
     */
    data class BadgeStatistics(
        val totalBadges: Int,
        val unlockedBadges: Int,
        val completionPercentage: Float,
        val commonBadges: Int,
        val rareBadges: Int,
        val epicBadges: Int,
        val legendaryBadges: Int,
        val recentlyUnlocked: List<Badge>
    )

    /**
     * Data class for badge progress information
     */
    data class BadgeProgress(
        val badge: Badge,
        val currentProgress: Int,
        val targetProgress: Int,
        val progressPercentage: Float,
        val isUnlocked: Boolean
    )
}

/**
 * Enum for different badge activity types
 */
enum class BadgeActivity {
    HABIT_COMPLETED,
    WORKOUT_COMPLETED,
    STRENGTH_WORKOUT,
    CARDIO_WORKOUT,
    HIIT_WORKOUT,
    FLEXIBILITY_WORKOUT,
    STREAK_MILESTONE,
    NUTRITION_FAVORITE,
    EARLY_BIRD,
    NIGHT_OWL,
    DAILY_GOAL_ACHIEVED,
    WEEKLY_GOAL_ACHIEVED,
    PERFECT_WEEK,
    OVERACHIEVER,
    CATEGORY_COMPLETED
}
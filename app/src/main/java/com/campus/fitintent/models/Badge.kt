package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Badge entity representing achievements and milestones
 * Badges are earned by completing various activities and reaching goals
 */
@Entity(tableName = "badges")
data class Badge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    val description: String,
    val type: BadgeType,
    val rarity: BadgeRarity,
    val iconResId: String, // Resource identifier for badge icon
    val targetProgress: Int, // Progress required to unlock badge
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

/**
 * Badge types categorizing different achievement areas
 */
enum class BadgeType {
    // Generic categories
    HABIT,        // Habit completion achievements
    WORKOUT,      // Workout-related achievements
    STREAK,       // Streak milestone achievements
    NUTRITION,    // Nutrition tip engagement achievements
    TIME,         // Time-based achievements (early bird, night owl)
    CONSISTENCY,  // Consistency and goal achievement badges
    SPECIAL,      // Special event or multi-category achievements

    // Specific badge achievements
    GOAL_GETTER,      // Completed daily goals
    CALORIE_CRUSHER,  // Calorie goal achievements
    STEP_MASTER,      // Step goal achievements (10k+ steps)
    WATER_CHAMPION,   // Water intake goal achievements
    CONSISTENCY_KING, // Long workout streaks (100+ days)
    MONTH_MASTER,     // Monthly workout streaks (30+ days)
    WEEK_WARRIOR,     // Weekly workout streaks (7+ days)
    HABIT_MASTER,     // Long habit streaks (21+ days)
    HABIT_BUILDER,    // Medium habit streaks (7+ days)
    HABIT_STARTER     // Beginning habit streaks (1+ days)
}

/**
 * Badge rarity levels determining visual presentation and point rewards
 */
enum class BadgeRarity {
    COMMON,      // Easy to achieve, basic badges
    RARE,        // Moderate difficulty, good progress indicators
    EPIC,        // Challenging achievements, significant milestones
    LEGENDARY    // Extremely rare, major accomplishments
}

/**
 * User badge progress and unlock status
 * Links users to their badge achievements and progress
 */
@Entity(
    tableName = "user_badges",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Badge::class,
            parentColumns = ["id"],
            childColumns = ["badgeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index("badgeId"),
        Index(value = ["userId", "badgeId"], unique = true)
    ]
)
data class UserBadge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val badgeId: Long,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedAt: Date? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    /**
     * Calculate progress percentage toward badge unlock
     */
    fun calculateProgressPercentage(targetProgress: Int): Float {
        return if (targetProgress > 0) {
            (currentProgress.toFloat() / targetProgress) * 100f
        } else {
            0f
        }
    }

    /**
     * Check if badge is close to being unlocked (within 10% of target)
     */
    fun isCloseToUnlock(targetProgress: Int): Boolean {
        return !isUnlocked && calculateProgressPercentage(targetProgress) >= 90f
    }
}

/**
 * Badge notification tracking
 * Tracks which badge unlocks have been shown to users
 */
@Entity(
    tableName = "badge_notifications",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Badge::class,
            parentColumns = ["id"],
            childColumns = ["badgeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index("badgeId"),
        Index(value = ["userId", "badgeId"], unique = true)
    ]
)
data class BadgeNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val badgeId: Long,
    val notificationType: BadgeNotificationType,
    val isShown: Boolean = false,
    val shownAt: Date? = null,
    val createdAt: Date = Date()
)

/**
 * Types of badge notifications
 */
enum class BadgeNotificationType {
    UNLOCK,          // Badge was unlocked
    PROGRESS_75,     // 75% progress toward badge
    PROGRESS_90,     // 90% progress toward badge
    STREAK_REMINDER  // Reminder to maintain streak for streak badges
}
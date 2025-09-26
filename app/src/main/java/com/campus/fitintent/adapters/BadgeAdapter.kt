package com.campus.fitintent.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ItemBadgeCompactBinding
import com.campus.fitintent.databinding.ItemBadgeProgressBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.viewmodels.BadgeDisplayItem
import com.campus.fitintent.utils.BadgeManager
import com.campus.fitintent.utils.PointCalculator
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for displaying badges in different formats
 * Supports both compact view (for recent achievements) and progress view (for upcoming badges)
 */
class BadgeAdapter(
    private val viewType: BadgeViewType,
    private val onBadgeClick: (Badge) -> Unit = {}
) : ListAdapter<BadgeDisplayItem, RecyclerView.ViewHolder>(BadgeDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_COMPACT = 1
        private const val VIEW_TYPE_PROGRESS = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            BadgeViewType.COMPACT -> VIEW_TYPE_COMPACT
            BadgeViewType.PROGRESS -> VIEW_TYPE_PROGRESS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COMPACT -> {
                val binding = ItemBadgeCompactBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CompactBadgeViewHolder(binding)
            }
            VIEW_TYPE_PROGRESS -> {
                val binding = ItemBadgeProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProgressBadgeViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is CompactBadgeViewHolder -> holder.bind(item)
            is ProgressBadgeViewHolder -> holder.bind(item)
        }
    }

    /**
     * ViewHolder for compact badge display (recent achievements)
     */
    inner class CompactBadgeViewHolder(
        private val binding: ItemBadgeCompactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BadgeDisplayItem) {
            with(binding) {
                // Badge info
                tvBadgeName.text = item.badge.name
                tvBadgeDescription.text = item.badge.description

                // Badge icon and background
                imgBadgeIcon.setImageResource(getBadgeIconResource(item.badge.iconResId))
                cardBadgeBackground.setCardBackgroundColor(getBadgeBackgroundColor(item.badge.rarity))

                // Rarity indicator
                chipRarity.text = item.badge.rarity.name
                chipRarity.setChipBackgroundColorResource(getBadgeRarityColor(item.badge.rarity))

                // Unlock date (if applicable)
                if (item.isUnlocked && item.unlockedAt != null) {
                    tvUnlockDate.visibility = View.VISIBLE
                    tvUnlockDate.text = formatUnlockDate(item.unlockedAt)
                } else {
                    tvUnlockDate.visibility = View.GONE
                }

                // Click handler
                root.setOnClickListener {
                    onBadgeClick(item.badge)
                }
            }
        }
    }

    /**
     * ViewHolder for progress badge display (upcoming badges)
     */
    inner class ProgressBadgeViewHolder(
        private val binding: ItemBadgeProgressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BadgeDisplayItem) {
            with(binding) {
                // Badge info
                tvBadgeName.text = item.badge.name
                tvBadgeDescription.text = item.badge.description

                // Badge icon and background
                imgBadgeIcon.setImageResource(getBadgeIconResource(item.badge.iconResId))

                // Progress-specific styling
                if (item.isUnlocked) {
                    cardBadgeBackground.setCardBackgroundColor(getBadgeBackgroundColor(item.badge.rarity))
                    imgBadgeIcon.setColorFilter(
                        root.context.getColor(R.color.on_primary),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                } else {
                    cardBadgeBackground.setCardBackgroundColor(root.context.getColor(R.color.surface_variant))
                    imgBadgeIcon.setColorFilter(
                        root.context.getColor(R.color.on_surface_variant),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }

                // Rarity indicator
                chipRarity.text = item.badge.rarity.name
                chipRarity.setChipBackgroundColorResource(getBadgeRarityColor(item.badge.rarity))

                // Progress information
                val progressPercent = ((item.currentProgress.toFloat() / item.badge.targetProgress) * 100).toInt()

                tvProgressText.text = "${item.currentProgress} / ${item.badge.targetProgress}"
                tvProgressPercentage.text = "$progressPercent%"

                // Progress indicators
                progressBadge.progress = progressPercent.coerceIn(0, 100)
                progressLinear.progress = progressPercent.coerceIn(0, 100)

                // Badge points reward
                tvBadgePoints.text = PointCalculator.calculateBadgePoints(item.badge).toString()

                // Progress bar colors based on completion
                val progressColor = when {
                    item.isUnlocked -> R.color.green_primary
                    progressPercent >= 75 -> R.color.orange_primary
                    progressPercent >= 50 -> R.color.yellow_primary
                    else -> R.color.blue_primary
                }

                progressBadge.setIndicatorColor(root.context.getColor(progressColor))
                progressLinear.setIndicatorColor(root.context.getColor(progressColor))

                // Click handler
                root.setOnClickListener {
                    onBadgeClick(item.badge)
                }
            }
        }
    }

    /**
     * Get badge icon resource based on badge type and icon identifier
     */
    private fun getBadgeIconResource(iconResId: String): Int {
        return when (iconResId) {
            "ic_badge_first_step" -> R.drawable.ic_footsteps
            "ic_badge_consistency" -> R.drawable.ic_calendar_check
            "ic_badge_habit_former" -> R.drawable.ic_target
            "ic_badge_lifestyle_master" -> R.drawable.ic_crown
            "ic_badge_first_sweat" -> R.drawable.ic_sweat_drop
            "ic_badge_fitness_enthusiast" -> R.drawable.ic_dumbbell
            "ic_badge_strength_master" -> R.drawable.ic_muscle
            "ic_badge_cardio_king" -> R.drawable.ic_heart
            "ic_badge_flexibility_pro" -> R.drawable.ic_stretch
            "ic_badge_hiit_hero" -> R.drawable.ic_lightning
            "ic_badge_getting_started" -> R.drawable.ic_star
            "ic_badge_week_warrior" -> R.drawable.ic_calendar_week
            "ic_badge_habit_former_streak" -> R.drawable.ic_fire
            "ic_badge_consistency_king" -> R.drawable.ic_crown
            "ic_badge_legend" -> R.drawable.ic_trophy
            "ic_badge_early_bird" -> R.drawable.ic_sunrise
            "ic_badge_night_owl" -> R.drawable.ic_moon
            "ic_badge_nutrition_explorer" -> R.drawable.ic_nutrition
            "ic_badge_hydration_hero" -> R.drawable.ic_water_drop
            "ic_badge_goal_crusher" -> R.drawable.ic_target
            "ic_badge_perfect_week" -> R.drawable.ic_calendar_check
            "ic_badge_overachiever" -> R.drawable.ic_trending_up
            "ic_badge_all_rounder" -> R.drawable.ic_360
            "ic_badge_transformer" -> R.drawable.ic_transform
            else -> R.drawable.ic_trophy // Default icon
        }
    }

    /**
     * Get badge background color based on rarity
     */
    private fun getBadgeBackgroundColor(rarity: BadgeRarity): Int {
        return when (rarity) {
            BadgeRarity.COMMON -> R.color.blue_primary
            BadgeRarity.RARE -> R.color.purple_primary
            BadgeRarity.EPIC -> R.color.orange_primary
            BadgeRarity.LEGENDARY -> R.color.yellow_primary
        }
    }

    /**
     * Get badge rarity color for chip
     */
    private fun getBadgeRarityColor(rarity: BadgeRarity): Int {
        return when (rarity) {
            BadgeRarity.COMMON -> R.color.blue_primary
            BadgeRarity.RARE -> R.color.purple_primary
            BadgeRarity.EPIC -> R.color.orange_primary
            BadgeRarity.LEGENDARY -> R.color.yellow_primary
        }
    }


    /**
     * Format unlock date for display
     */
    private fun formatUnlockDate(date: Date): String {
        val now = Date()
        val diffInMillis = now.time - date.time
        val diffInDays = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()

        return when {
            diffInDays == 0 -> "Unlocked today"
            diffInDays == 1 -> "Unlocked yesterday"
            diffInDays < 7 -> "Unlocked $diffInDays days ago"
            diffInDays < 30 -> {
                val weeks = diffInDays / 7
                if (weeks == 1) "Unlocked 1 week ago" else "Unlocked $weeks weeks ago"
            }
            else -> {
                val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                "Unlocked ${formatter.format(date)}"
            }
        }
    }

    /**
     * DiffUtil callback for efficient list updates
     */
    private class BadgeDiffCallback : DiffUtil.ItemCallback<BadgeDisplayItem>() {
        override fun areItemsTheSame(oldItem: BadgeDisplayItem, newItem: BadgeDisplayItem): Boolean {
            return oldItem.badge.id == newItem.badge.id
        }

        override fun areContentsTheSame(oldItem: BadgeDisplayItem, newItem: BadgeDisplayItem): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Enum for different badge view types
 */
enum class BadgeViewType {
    COMPACT,    // For recent achievements horizontal list
    PROGRESS    // For upcoming badges with progress bars
}

// BadgeDisplayItem is defined in viewmodels package

/**
 * Extension functions for easy adapter usage
 */
fun List<Badge>.toCompactDisplayItems(unlockedBadges: List<UserBadge>): List<BadgeDisplayItem> {
    return this.map { badge ->
        val userBadge = unlockedBadges.find { it.badgeId == badge.id }
        BadgeDisplayItem(
            badge = badge,
            progress = if (userBadge?.isUnlocked == true) 100f else 0f,
            isUnlocked = userBadge?.isUnlocked == true,
            pointValue = PointCalculator.calculateBadgePoints(badge),
            currentProgress = badge.targetProgress, // Full progress for unlocked
            targetProgress = badge.targetProgress,
            unlockedAt = userBadge?.unlockedAt
        )
    }
}

fun List<BadgeManager.BadgeProgress>.toProgressDisplayItems(): List<BadgeDisplayItem> {
    return this.map { badgeProgress ->
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
}
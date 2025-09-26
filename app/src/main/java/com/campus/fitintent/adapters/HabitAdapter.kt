package com.campus.fitintent.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ItemHabitBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.DateUtils
import java.text.NumberFormat

/**
 * Adapter for displaying habits in RecyclerView
 * Handles habit display, completion checkboxes, and more options menu
 */
class HabitAdapter(
    private val onHabitComplete: (Habit, Boolean) -> Unit,
    private val onHabitEdit: (Habit) -> Unit,
    private val onHabitDelete: (Habit) -> Unit,
    private val onHabitPause: (Habit) -> Unit,
    private val onHabitDetails: (Habit) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.bind(habit)
    }

    inner class HabitViewHolder(
        private val binding: ItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            with(binding) {
                // Basic habit info
                tvHabitName.text = habit.name
                tvHabitDescription.text = habit.description.ifEmpty {
                    root.context.getString(R.string.no_description)
                }

                // Category chip
                chipCategory.text = getCategoryDisplayName(habit.category)

                // Color indicator
                viewColorIndicator.setBackgroundColor(
                    try {
                        Color.parseColor(habit.color)
                    } catch (e: Exception) {
                        Color.parseColor("#4CAF50") // Default green
                    }
                )

                // Completion checkbox
                val isCompletedToday = isHabitCompletedToday(habit)
                checkboxCompleted.isChecked = isCompletedToday
                checkboxCompleted.isEnabled = habit.isActive && !habit.isPaused

                // Progress information
                tvCurrentStreakValue.text = habit.currentStreak.toString()

                val completionRate = habit.getCompletionPercentage()
                tvCompletionRate.text = NumberFormat.getInstance().apply {
                    maximumFractionDigits = 0
                }.format(completionRate) + "%"

                // Days remaining for 21-day habit
                val daysRemaining = maxOf(0, habit.targetDays - habit.completedDays)
                tvDaysRemaining.text = if (daysRemaining > 0) {
                    root.context.resources.getQuantityString(
                        R.plurals.days_remaining,
                        daysRemaining,
                        daysRemaining
                    )
                } else {
                    root.context.getString(R.string.habit_completed_congratulations)
                }

                // Progress bar
                progressBar.progress = completionRate.toInt()

                // Handle completion checkbox
                checkboxCompleted.setOnCheckedChangeListener { _, isChecked ->
                    if (habit.isActive && !habit.isPaused) {
                        onHabitComplete(habit, isChecked)
                    }
                }

                // Handle more options menu
                btnMoreOptions.setOnClickListener { view ->
                    showMoreOptionsMenu(view, habit)
                }

                // Handle item click for details
                root.setOnClickListener {
                    onHabitDetails(habit)
                }

                // Visual state for paused habits
                if (habit.isPaused) {
                    root.alpha = 0.6f
                    tvHabitName.text = "${habit.name} ${root.context.getString(R.string.paused)}"
                } else {
                    root.alpha = 1.0f
                }

                // Visual state for completed habits
                if (habit.isCompleted) {
                    progressBar.progress = 100
                    tvDaysRemaining.text = root.context.getString(R.string.habit_completed_congratulations)
                }
            }
        }

        private fun showMoreOptionsMenu(view: android.view.View, habit: Habit) {
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.habit_item_menu, popup.menu)

            // Configure menu items based on habit state
            popup.menu.findItem(R.id.action_pause)?.let { pauseItem ->
                pauseItem.title = if (habit.isPaused) {
                    view.context.getString(R.string.resume_habit)
                } else {
                    view.context.getString(R.string.pause_habit)
                }
                pauseItem.isVisible = habit.isActive && !habit.isCompleted
            }

            popup.menu.findItem(R.id.action_edit)?.isVisible = !habit.isCompleted
            popup.menu.findItem(R.id.action_delete)?.isVisible = true

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onHabitEdit(habit)
                        true
                    }
                    R.id.action_pause -> {
                        onHabitPause(habit)
                        true
                    }
                    R.id.action_delete -> {
                        onHabitDelete(habit)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        private fun getCategoryDisplayName(category: HabitCategory): String {
            return when (category) {
                HabitCategory.EXERCISE -> binding.root.context.getString(R.string.category_exercise)
                HabitCategory.NUTRITION -> binding.root.context.getString(R.string.category_nutrition)
                HabitCategory.HYDRATION -> binding.root.context.getString(R.string.category_hydration)
                HabitCategory.SLEEP -> binding.root.context.getString(R.string.category_sleep)
                HabitCategory.MINDFULNESS -> binding.root.context.getString(R.string.category_mindfulness)
                HabitCategory.STRETCHING -> binding.root.context.getString(R.string.category_stretching)
                HabitCategory.CARDIO -> binding.root.context.getString(R.string.category_cardio)
                HabitCategory.STRENGTH -> binding.root.context.getString(R.string.category_strength)
                HabitCategory.OTHER -> binding.root.context.getString(R.string.category_other)
            }
        }

        private fun isHabitCompletedToday(habit: Habit): Boolean {
            // This would typically check against HabitLog for today's date
            // For now, we'll use a simple heuristic based on habit state
            // In a real implementation, this would query the HabitLogDao
            return habit.currentStreak > 0 && DateUtils.isToday(habit.updatedAt)
        }
    }

    private class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Simplified habit data for quick overview
 * Used for displaying basic habit information in lists
 */
data class HabitDisplayInfo(
    val id: Long,
    val name: String,
    val description: String,
    val category: HabitCategory,
    val currentStreak: Int,
    val completionPercentage: Float,
    val daysRemaining: Int,
    val isCompletedToday: Boolean,
    val isPaused: Boolean,
    val isCompleted: Boolean,
    val color: String
)

/**
 * Extension function to convert Habit to HabitDisplayInfo
 */
fun Habit.toDisplayInfo(isCompletedToday: Boolean = false): HabitDisplayInfo {
    return HabitDisplayInfo(
        id = id,
        name = name,
        description = description,
        category = category,
        currentStreak = currentStreak,
        completionPercentage = getCompletionPercentage(),
        daysRemaining = maxOf(0, targetDays - completedDays),
        isCompletedToday = isCompletedToday,
        isPaused = isPaused,
        isCompleted = isCompleted,
        color = color
    )
}
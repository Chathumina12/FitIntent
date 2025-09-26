package com.campus.fitintent.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ItemWorkoutFeaturedBinding
import com.campus.fitintent.models.*

/**
 * Adapter for displaying featured workouts in RecyclerView
 * Handles workout display and start workout clicks
 */
class WorkoutAdapter(
    private val onWorkoutClick: (Workout) -> Unit,
    private val onStartWorkout: (Workout) -> Unit
) : ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutFeaturedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }

    inner class WorkoutViewHolder(
        private val binding: ItemWorkoutFeaturedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: Workout) {
            with(binding) {
                // Basic workout info
                tvWorkoutName.text = workout.name
                tvWorkoutDescription.text = workout.description

                // Duration and calories
                tvDuration.text = root.context.getString(R.string.duration_format, workout.durationMinutes)
                tvCalories.text = workout.caloriesBurned.toString()

                // Difficulty chip
                chipDifficulty.text = getDifficultyDisplayName(workout.difficulty)

                // Category chip
                chipCategory.text = getCategoryDisplayName(workout.category)

                // Workout icon based on category
                imgWorkoutIcon.setImageResource(getCategoryIcon(workout.category))

                // Set card background color based on category
                cardWorkoutIcon.setCardBackgroundColor(getCategoryColor(workout.category))

                // Click handlers
                root.setOnClickListener {
                    onWorkoutClick(workout)
                }

                btnStartWorkout.setOnClickListener {
                    onStartWorkout(workout)
                }
            }
        }

        private fun getDifficultyDisplayName(difficulty: DifficultyLevel): String {
            return when (difficulty) {
                DifficultyLevel.BEGINNER -> binding.root.context.getString(R.string.difficulty_beginner)
                DifficultyLevel.INTERMEDIATE -> binding.root.context.getString(R.string.difficulty_intermediate)
                DifficultyLevel.ADVANCED -> binding.root.context.getString(R.string.difficulty_advanced)
                DifficultyLevel.EXPERT -> binding.root.context.getString(R.string.difficulty_expert)
            }
        }

        private fun getCategoryDisplayName(category: WorkoutCategory): String {
            return when (category) {
                WorkoutCategory.CARDIO -> binding.root.context.getString(R.string.cardio)
                WorkoutCategory.STRENGTH -> binding.root.context.getString(R.string.strength)
                WorkoutCategory.FLEXIBILITY -> binding.root.context.getString(R.string.flexibility)
                WorkoutCategory.HIIT -> binding.root.context.getString(R.string.hiit)
                WorkoutCategory.YOGA -> binding.root.context.getString(R.string.yoga)
                WorkoutCategory.PILATES -> binding.root.context.getString(R.string.pilates)
                WorkoutCategory.SPORTS -> binding.root.context.getString(R.string.sports)
                WorkoutCategory.RECOVERY -> binding.root.context.getString(R.string.recovery)
                WorkoutCategory.FULL_BODY -> binding.root.context.getString(R.string.full_body)
            }
        }

        private fun getCategoryIcon(category: WorkoutCategory): Int {
            return when (category) {
                WorkoutCategory.CARDIO -> R.drawable.ic_run
                WorkoutCategory.STRENGTH -> R.drawable.ic_fitness
                WorkoutCategory.FLEXIBILITY -> R.drawable.ic_yoga
                WorkoutCategory.HIIT -> R.drawable.ic_timer
                WorkoutCategory.YOGA -> R.drawable.ic_yoga
                WorkoutCategory.PILATES -> R.drawable.ic_self_improvement
                WorkoutCategory.SPORTS -> R.drawable.ic_sports
                WorkoutCategory.RECOVERY -> R.drawable.ic_spa
                WorkoutCategory.FULL_BODY -> R.drawable.ic_fitness_center
            }
        }

        private fun getCategoryColor(category: WorkoutCategory): Int {
            return when (category) {
                WorkoutCategory.CARDIO -> binding.root.context.getColor(R.color.red_primary)
                WorkoutCategory.STRENGTH -> binding.root.context.getColor(R.color.blue_primary)
                WorkoutCategory.FLEXIBILITY -> binding.root.context.getColor(R.color.green_primary)
                WorkoutCategory.HIIT -> binding.root.context.getColor(R.color.orange_primary)
                WorkoutCategory.YOGA -> binding.root.context.getColor(R.color.purple_primary)
                WorkoutCategory.PILATES -> binding.root.context.getColor(R.color.teal_primary)
                WorkoutCategory.SPORTS -> binding.root.context.getColor(R.color.indigo_primary)
                WorkoutCategory.RECOVERY -> binding.root.context.getColor(R.color.cyan_primary)
                WorkoutCategory.FULL_BODY -> binding.root.context.getColor(R.color.red_primary)
            }
        }
    }

    private class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Adapter for displaying recent workout activity
 */
class WorkoutActivityAdapter(
    private val onActivityClick: (WorkoutSession) -> Unit = {}
) : ListAdapter<WorkoutSession, WorkoutActivityAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = com.campus.fitintent.databinding.ItemWorkoutActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = getItem(position)
        holder.bind(activity)
    }

    inner class ActivityViewHolder(
        private val binding: com.campus.fitintent.databinding.ItemWorkoutActivityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(session: WorkoutSession) {
            with(binding) {
                // Activity title
                tvActivityTitle.text = root.context.getString(
                    R.string.workout_completed_title,
                    "Workout" // TODO: Get actual workout name from session
                )

                // Activity details
                val duration = session.calculateDuration()
                val calories = session.caloriesBurned ?: 0
                tvActivityDetails.text = root.context.getString(
                    R.string.workout_activity_details,
                    duration,
                    calories
                )

                // Activity time (relative time)
                tvActivityTime.text = getRelativeTimeString(session.endTime?.time ?: session.startTime.time)

                // Show achievement badge for special milestones
                if (shouldShowAchievement(session)) {
                    chipAchievement.visibility = android.view.View.VISIBLE
                    chipAchievement.text = getAchievementText(session)
                } else {
                    chipAchievement.visibility = android.view.View.GONE
                }

                // Activity icon based on completion
                if (session.isCompleted) {
                    imgActivityIcon.setImageResource(R.drawable.ic_check_circle)
                    imgActivityIcon.setColorFilter(
                        root.context.getColor(R.color.green_primary),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                } else {
                    imgActivityIcon.setImageResource(R.drawable.ic_pause_circle)
                    imgActivityIcon.setColorFilter(
                        root.context.getColor(R.color.orange_primary),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }

                // Click handler
                root.setOnClickListener {
                    onActivityClick(session)
                }
            }
        }

        private fun shouldShowAchievement(session: WorkoutSession): Boolean {
            // Show achievement for completed workouts with high ratings
            return session.isCompleted && (session.feelingRating ?: 0) >= 4
        }

        private fun getAchievementText(session: WorkoutSession): String {
            return when {
                (session.feelingRating ?: 0) >= 5 -> "Perfect!"
                session.completionPercentage >= 100f -> "Complete!"
                else -> "Great Job!"
            }
        }

        private fun getRelativeTimeString(timeMillis: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timeMillis
            val minutes = diff / (1000 * 60)
            val hours = minutes / 60
            val days = hours / 24

            return when {
                minutes < 1 -> "Just now"
                minutes < 60 -> "${minutes}m ago"
                hours < 24 -> "${hours}h ago"
                days < 7 -> "${days}d ago"
                else -> "${days / 7}w ago"
            }
        }
    }

    private class ActivityDiffCallback : DiffUtil.ItemCallback<WorkoutSession>() {
        override fun areItemsTheSame(oldItem: WorkoutSession, newItem: WorkoutSession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutSession, newItem: WorkoutSession): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Extension function to convert minutes to readable duration format
 */
fun Int.toReadableDuration(): String {
    return if (this >= 60) {
        val hours = this / 60
        val minutes = this % 60
        if (minutes > 0) "${hours}h ${minutes}m" else "${hours}h"
    } else {
        "${this}m"
    }
}
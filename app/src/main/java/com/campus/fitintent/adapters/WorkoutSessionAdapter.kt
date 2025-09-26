package com.campus.fitintent.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campus.fitintent.databinding.ItemWorkoutSessionBinding
import com.campus.fitintent.models.WorkoutSession
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for displaying recent workout sessions in RecyclerView
 * Shows completed workout sessions with duration and calories burned
 */
class WorkoutSessionAdapter(
    private val onSessionClick: (WorkoutSession) -> Unit
) : ListAdapter<WorkoutSession, WorkoutSessionAdapter.SessionViewHolder>(SessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemWorkoutSessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(getItem(position), onSessionClick)
    }

    class SessionViewHolder(
        private val binding: ItemWorkoutSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

        fun bind(session: WorkoutSession, onSessionClick: (WorkoutSession) -> Unit) {
            binding.apply {
                // For now, display basic session info
                // TODO: Get workout name from workoutId via repository
                textWorkoutName.text = "Workout Session ${session.id}"
                textDuration.text = "${session.durationMinutes ?: 0} min"
                textCalories.text = "${session.caloriesBurned ?: 0} cal"

                session.endTime?.let { endTime ->
                    textDate.text = dateFormat.format(endTime)
                    textTime.text = timeFormat.format(endTime)
                }

                // Show completion status
                textCompletion.text = "${session.completionPercentage.toInt()}% complete"

                root.setOnClickListener {
                    onSessionClick(session)
                }
            }
        }
    }

    class SessionDiffCallback : DiffUtil.ItemCallback<WorkoutSession>() {
        override fun areItemsTheSame(oldItem: WorkoutSession, newItem: WorkoutSession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutSession, newItem: WorkoutSession): Boolean {
            return oldItem == newItem
        }
    }
}
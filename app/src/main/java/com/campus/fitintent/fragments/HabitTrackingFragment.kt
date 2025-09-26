package com.campus.fitintent.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.R
import com.campus.fitintent.adapters.HabitAdapter
import com.campus.fitintent.databinding.FragmentHabitTrackingBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.Result
import com.campus.fitintent.viewmodels.HabitViewModel
import com.campus.fitintent.viewmodels.HabitStatistics
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.FitIntentApplication
import java.text.NumberFormat

/**
 * Fragment for tracking habits with 21-day formation system
 * Features streak summary, today's progress, and habit management
 */
class HabitTrackingFragment : Fragment() {

    private var _binding: FragmentHabitTrackingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUI()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()

        // Load initial data
        loadHabitData()
    }

    private fun setupViewModel() {
        preferencesManager = PreferencesManager(requireContext())

        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory.getInstance(app)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]
    }

    private fun setupUI() {
        // Initial UI state
        updateEmptyState(true) // Show empty state initially
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(
            onHabitComplete = { habit, isCompleted ->
                handleHabitCompletion(habit, isCompleted)
            },
            onHabitEdit = { habit ->
                // TODO: Navigate to habit edit screen
                Toast.makeText(context, "Edit habit: ${habit.name}", Toast.LENGTH_SHORT).show()
            },
            onHabitDelete = { habit ->
                showDeleteConfirmation(habit)
            },
            onHabitPause = { habit ->
                viewModel.toggleHabitPause(habit.id)
            },
            onHabitDetails = { habit ->
                // TODO: Navigate to habit details screen
                Toast.makeText(context, "Habit details: ${habit.name}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewHabits.apply {
            adapter = habitAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddHabit.setOnClickListener {
            // TODO: Navigate to add habit screen
            showAddHabitDialog()
        }

        binding.btnCreateFirstHabit.setOnClickListener {
            // TODO: Navigate to add habit screen
            showAddHabitDialog()
        }

        binding.btnManageHabits.setOnClickListener {
            // TODO: Navigate to habit management screen
            Toast.makeText(context, "Navigate to Habit Management", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        // Observe all habits
        viewModel.allHabits.observe(viewLifecycleOwner) { habits ->
            habitAdapter.submitList(habits)
            updateEmptyState(habits.isEmpty())
            updateStreakSummary(habits)
        }

        // Observe habit statistics
        viewModel.getHabitStats().observe(viewLifecycleOwner) { stats ->
            updateProgressStats(stats)
        }

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe error state
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }

        // Observe action state (for completion, deletion, etc.)
        viewModel.actionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    // Action completed successfully
                    viewModel.clearStates()
                }
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                    viewModel.clearStates()
                }
                is Result.Loading -> {
                    // Handle loading if needed
                }
                null -> {
                    // No action state
                }
            }
        }

        // Observe habit creation state
        viewModel.creationState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(
                        context,
                        getString(R.string.habit_created_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.clearStates()
                }
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                    viewModel.clearStates()
                }
                is Result.Loading -> {
                    // Handle loading state
                }
                null -> {
                    // No creation state
                }
            }
        }
    }

    private fun loadHabitData() {
        // Data loading is handled automatically by LiveData observers
        // ViewModel uses Flow/LiveData from Repository which is reactive
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.layoutEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerViewHabits.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun updateStreakSummary(habits: List<Habit>) {
        val currentStreak = habits.maxOfOrNull { it.currentStreak } ?: 0
        val completedToday = habits.count { isHabitCompletedToday(it) }
        val totalHabits = habits.size

        binding.tvCurrentStreak.text = currentStreak.toString()
        binding.tvTodayProgress.text = "$completedToday/$totalHabits"

        // Calculate weekly goal progress (simplified)
        val weeklyGoalProgress = if (totalHabits > 0) {
            ((completedToday.toFloat() / totalHabits.toFloat()) * 100).toInt()
        } else 0

        binding.tvWeeklyGoal.text = "$weeklyGoalProgress%"
    }

    private fun updateProgressStats(stats: HabitStatistics) {
        // Update additional statistics if needed
        // This is already handled in updateStreakSummary for basic stats
    }

    private fun handleHabitCompletion(habit: Habit, isCompleted: Boolean) {
        if (isCompleted) {
            viewModel.completeHabitToday(habit.id)
            showCompletionEncouragement(habit)
        } else {
            // TODO: Handle unchecking a completed habit
            // This might require additional repository methods
            Toast.makeText(
                context,
                getString(R.string.habit_unmarked_completion),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showCompletionEncouragement(habit: Habit) {
        val messages = listOf(
            getString(R.string.great_job_habit_completed),
            getString(R.string.keep_up_good_work),
            getString(R.string.one_step_closer_to_goal),
            getString(R.string.building_healthy_habits)
        )

        val message = messages.random()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        // Show special message for streaks
        if (habit.currentStreak > 0 && habit.currentStreak % 7 == 0) {
            Toast.makeText(
                context,
                getString(R.string.weekly_streak_milestone, habit.currentStreak),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showDeleteConfirmation(habit: Habit) {
        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.delete_habit))
            .setMessage(getString(R.string.delete_habit_confirmation, habit.name))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteHabit(habit.id)
                Toast.makeText(
                    context,
                    getString(R.string.habit_deleted),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showAddHabitDialog() {
        // Simple demo habit creation
        // TODO: Replace with proper AddHabitDialogFragment
        val habitNames = listOf(
            "Morning Exercise",
            "Drink 8 Glasses of Water",
            "Read for 30 Minutes",
            "Practice Meditation",
            "Take 10,000 Steps"
        )

        val categories = listOf(
            HabitCategory.EXERCISE,
            HabitCategory.HYDRATION,
            HabitCategory.MINDFULNESS,
            HabitCategory.MINDFULNESS,
            HabitCategory.CARDIO
        )

        val descriptions = listOf(
            "Start the day with 15 minutes of exercise",
            "Stay hydrated throughout the day",
            "Expand knowledge and improve focus",
            "Practice mindfulness and reduce stress",
            "Stay active and maintain fitness"
        )

        val items = habitNames.toTypedArray()

        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.choose_habit_to_add))
            .setItems(items) { _, which ->
                viewModel.createHabit(
                    name = habitNames[which],
                    description = descriptions[which],
                    category = categories[which].name,
                    frequency = "DAILY",
                    reminderEnabled = true,
                    reminderTime = "08:00"
                )
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun isHabitCompletedToday(habit: Habit): Boolean {
        // Simplified check - in real implementation, check HabitLog for today
        // For now, assume recent update means completed today
        val today = System.currentTimeMillis()
        val habitUpdated = habit.updatedAt.time
        val oneDayMs = 24 * 60 * 60 * 1000

        return (today - habitUpdated) < oneDayMs && habit.currentStreak > 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HabitTrackingFragment()

        const val TAG = "HabitTrackingFragment"
    }
}
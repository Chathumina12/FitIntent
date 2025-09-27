package com.campus.fitintent.fragments

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
import com.campus.fitintent.adapters.WorkoutAdapter
import com.campus.fitintent.adapters.WorkoutSessionAdapter
import com.campus.fitintent.databinding.FragmentWorkoutBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.WorkoutRepository
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.Result
import com.campus.fitintent.viewmodels.WorkoutViewModel
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.FitIntentApplication

/**
 * Fragment for displaying workouts, categories, and recent activity
 * Features workout library, quick start, and category navigation
 */
class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var activityAdapter: WorkoutSessionAdapter
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()

        // Load initial data
        loadWorkoutData()
    }

    private fun setupViewModel() {
        preferencesManager = PreferencesManager(requireContext())

        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory.getInstance(app)
        viewModel = ViewModelProvider(this, factory)[WorkoutViewModel::class.java]
    }

    private fun setupRecyclerViews() {
        // Featured workouts adapter
        workoutAdapter = WorkoutAdapter(
            onWorkoutClick = { workout ->
                navigateToWorkoutDetail(workout)
            },
            onStartWorkout = { workout ->
                startWorkout(workout)
            }
        )

        binding.recyclerViewFeaturedWorkouts.apply {
            adapter = workoutAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }

        // Recent activity adapter
        activityAdapter = WorkoutSessionAdapter { session ->
            // Navigate to workout session details
            Toast.makeText(
                context,
                "View session details: ${session.id}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.recyclerViewRecentActivity.apply {
            adapter = activityAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    private fun setupClickListeners() {
        // Quick Start button
        binding.btnQuickStart.setOnClickListener {
            startQuickWorkout()
        }

        // Search button
        binding.btnSearch.setOnClickListener {
            // TODO: Navigate to workout search
            Toast.makeText(context, "Search workouts", Toast.LENGTH_SHORT).show()
        }

        // Category cards
        binding.cardStrength.setOnClickListener {
            navigateToCategory(WorkoutCategory.STRENGTH)
        }

        binding.cardCardio.setOnClickListener {
            navigateToCategory(WorkoutCategory.CARDIO)
        }

        binding.cardHiit.setOnClickListener {
            navigateToCategory(WorkoutCategory.HIIT)
        }

        binding.cardFlexibility.setOnClickListener {
            navigateToCategory(WorkoutCategory.FLEXIBILITY)
        }

        // View all workouts
        binding.btnViewAllWorkouts.setOnClickListener {
            navigateToAllWorkouts()
        }
    }

    private fun observeViewModel() {
        // Observe featured workouts
        viewModel.featuredWorkouts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressLoading.visibility = View.GONE
                    workoutAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.progressLoading.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Failed to load workouts: ${result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        // Observe recent activity
        viewModel.recentActivity.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Loading handled by main progress indicator
                }
                is Result.Success -> {
                    val activities = result.data
                    activityAdapter.submitList(activities)
                    updateEmptyActivityState(activities.isEmpty())
                }
                is Result.Error -> {
                    updateEmptyActivityState(true)
                }
            }
        }

        // Observe workout category counts
        viewModel.categoryCounts.observe(viewLifecycleOwner) { counts ->
            updateCategoryCards(counts)
        }

        // Observe last workout for quick start
        viewModel.lastWorkout.observe(viewLifecycleOwner) { workout ->
            updateQuickStartCard(workout)
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
    }

    private fun loadWorkoutData() {
        viewModel.loadFeaturedWorkouts()
        viewModel.loadRecentActivity()
        viewModel.loadCategoryCounts()
        viewModel.loadLastWorkout()
    }

    private fun updateCategoryCards(counts: Map<WorkoutCategory, Int>) {
        binding.tvStrengthCount.text = resources.getQuantityString(
            R.plurals.workout_count,
            counts[WorkoutCategory.STRENGTH] ?: 0,
            counts[WorkoutCategory.STRENGTH] ?: 0
        )

        binding.tvCardioCount.text = resources.getQuantityString(
            R.plurals.workout_count,
            counts[WorkoutCategory.CARDIO] ?: 0,
            counts[WorkoutCategory.CARDIO] ?: 0
        )

        binding.tvHiitCount.text = resources.getQuantityString(
            R.plurals.workout_count,
            counts[WorkoutCategory.HIIT] ?: 0,
            counts[WorkoutCategory.HIIT] ?: 0
        )

        binding.tvFlexibilityCount.text = resources.getQuantityString(
            R.plurals.workout_count,
            counts[WorkoutCategory.FLEXIBILITY] ?: 0,
            counts[WorkoutCategory.FLEXIBILITY] ?: 0
        )
    }

    private fun updateQuickStartCard(workout: Workout?) {
        if (workout != null) {
            binding.tvQuickStartDesc.text = getString(R.string.continue_workout, workout.name)
        } else {
            binding.tvQuickStartDesc.text = getString(R.string.start_recommended_workout)
        }
    }

    private fun updateEmptyActivityState(isEmpty: Boolean) {
        binding.layoutEmptyActivity.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerViewRecentActivity.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun startQuickWorkout() {
        viewModel.lastWorkout.value?.let { workout ->
            startWorkout(workout)
        } ?: run {
            // Start a recommended workout
            viewModel.featuredWorkouts.value?.let { result ->
                if (result is Result.Success && result.data.isNotEmpty()) {
                    startWorkout(result.data.first())
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.no_workouts_available),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun startWorkout(workout: Workout) {
        // TODO: Navigate to workout timer/detail screen
        Toast.makeText(
            context,
            getString(R.string.starting_workout, workout.name),
            Toast.LENGTH_SHORT
        ).show()

        // For now, simulate starting a workout
        viewModel.startWorkoutSession(workout.id)
    }

    private fun navigateToWorkoutDetail(workout: Workout) {
        // TODO: Navigate to workout detail screen
        Toast.makeText(
            context,
            getString(R.string.viewing_workout, workout.name),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToCategory(category: WorkoutCategory) {
        // TODO: Navigate to category-specific workout list
        val categoryName = getCategoryDisplayName(category)
        Toast.makeText(
            context,
            getString(R.string.opening_category_workouts, categoryName),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToAllWorkouts() {
        // TODO: Navigate to all workouts screen
        Toast.makeText(
            context,
            getString(R.string.opening_all_workouts),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getCategoryDisplayName(category: WorkoutCategory): String {
        return when (category) {
            WorkoutCategory.CARDIO -> getString(R.string.cardio)
            WorkoutCategory.STRENGTH -> getString(R.string.strength)
            WorkoutCategory.FLEXIBILITY -> getString(R.string.flexibility)
            WorkoutCategory.HIIT -> getString(R.string.hiit)
            WorkoutCategory.YOGA -> getString(R.string.yoga)
            WorkoutCategory.PILATES -> getString(R.string.pilates)
            WorkoutCategory.SPORTS -> getString(R.string.sports)
            WorkoutCategory.RECOVERY -> getString(R.string.recovery)
            WorkoutCategory.FULL_BODY -> getString(R.string.full_body)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = WorkoutFragment()

        const val TAG = "WorkoutFragment"
    }
}

/**
 * ViewModelFactory for WorkoutViewModel dependency injection
 */
class WorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(workoutRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
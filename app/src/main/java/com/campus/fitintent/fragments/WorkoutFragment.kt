package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.databinding.FragmentWorkoutBinding
import com.campus.fitintent.utils.ViewModelFactory
import com.campus.fitintent.viewmodels.WorkoutViewModel
import com.google.android.material.snackbar.Snackbar

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutViewModel: WorkoutViewModel

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

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory(workoutRepository = app.workoutRepository)
        workoutViewModel = ViewModelProvider(this, factory)[WorkoutViewModel::class.java]

        setupUI()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupUI() {
        // Setup RecyclerViews
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.recentActivityRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        // Observe featured workouts
        workoutViewModel.featuredWorkouts.observe(viewLifecycleOwner) { workouts ->
            // Update featured workouts adapter when implemented
        }

        // Observe recent activity
        workoutViewModel.recentActivity.observe(viewLifecycleOwner) { activities ->
            // Update recent activity adapter when implemented
        }

        // Observe workout categories count
        workoutViewModel.categoryCounts.observe(viewLifecycleOwner) { counts ->
            // Update category counts in the UI
            updateCategoryCards(counts)
        }
    }

    private fun setupClickListeners() {
        // Quick Start button
        binding.quickStartButton.setOnClickListener {
            startQuickWorkout()
        }

        // Category cards
        binding.strengthCard.setOnClickListener {
            navigateToCategory("strength")
        }

        binding.cardioCard.setOnClickListener {
            navigateToCategory("cardio")
        }

        binding.flexibilityCard.setOnClickListener {
            navigateToCategory("flexibility")
        }

        // View all workouts
        binding.viewAllWorkouts.setOnClickListener {
            navigateToAllWorkouts()
        }
    }

    private fun startQuickWorkout() {
        // Start the last workout or a recommended one
        workoutViewModel.getLastWorkout()?.let { workout ->
            // Navigate to workout detail/timer screen
            Snackbar.make(binding.root, "Starting: ${workout.name}", Snackbar.LENGTH_SHORT).show()
        } ?: run {
            Snackbar.make(binding.root, "No recent workouts found", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun navigateToCategory(category: String) {
        // Navigate to category-specific workout list
        Snackbar.make(binding.root, "Opening $category workouts", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToAllWorkouts() {
        // Navigate to all workouts screen
        Snackbar.make(binding.root, "Opening all workouts", Snackbar.LENGTH_SHORT).show()
    }

    private fun updateCategoryCards(counts: Map<String, Int>) {
        // Update category card counts
        // This will be implemented when we have the actual data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
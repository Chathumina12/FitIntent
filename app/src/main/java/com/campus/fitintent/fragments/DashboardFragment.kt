package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.databinding.FragmentDashboardBinding
<<<<<<< HEAD
import com.campus.fitintent.utils.ViewModelFactory
=======
import com.campus.fitintent.viewmodels.ViewModelFactory
>>>>>>> 818ab1f (Updated)
import com.campus.fitintent.viewmodels.DashboardViewModel
import java.util.Calendar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
<<<<<<< HEAD
        val factory = ViewModelFactory(app.userRepository)
=======
        val factory = ViewModelFactory.getInstance(app)
>>>>>>> 818ab1f (Updated)
        dashboardViewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]

        setupUI()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupUI() {
        // Set greeting based on time of day
        val greeting = getGreeting()
        binding.greetingText.text = greeting

        // Setup RecyclerViews
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.achievementsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> "Good Morning"
            hour < 17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private fun observeViewModel() {
<<<<<<< HEAD
        // Observe user data
        dashboardViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.userNameText.text = it.name
            }
=======
        // Observe user name
        dashboardViewModel.name.observe(viewLifecycleOwner) { userName ->
            binding.userNameText.text = userName
>>>>>>> 818ab1f (Updated)
        }

        // Observe daily progress
        dashboardViewModel.dailyProgress.observe(viewLifecycleOwner) { progress ->
            binding.dailyProgressBar.progress = progress.progressPercentage
            binding.progressPercentText.text = "${progress.progressPercentage}% Complete"
            binding.dailyGoalText.text = progress.goalText
        }

        // Observe streak
        dashboardViewModel.currentStreak.observe(viewLifecycleOwner) { streak ->
            binding.streakCountText.text = streak.toString()
        }

        // Observe points
        dashboardViewModel.totalPoints.observe(viewLifecycleOwner) { points ->
            binding.pointsText.text = points.toString()
        }

        // Observe today's habits
        dashboardViewModel.todaysHabits.observe(viewLifecycleOwner) { habits ->
            // Update habits adapter when implemented
        }

        // Observe recent achievements
        dashboardViewModel.recentAchievements.observe(viewLifecycleOwner) { achievements ->
            // Update achievements adapter when implemented
        }
    }

    private fun setupClickListeners() {
        binding.profileImage.setOnClickListener {
            // Navigate to profile
        }

        binding.startWorkoutButton.setOnClickListener {
            // Navigate to workout section
        }

        binding.trackHabitButton.setOnClickListener {
            // Open habit tracking dialog
        }

        binding.viewAllHabits.setOnClickListener {
            // Navigate to habits screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
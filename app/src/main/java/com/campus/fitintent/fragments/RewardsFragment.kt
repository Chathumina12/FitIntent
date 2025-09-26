package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.adapters.BadgeAdapter
import com.campus.fitintent.adapters.BadgeViewType
import com.campus.fitintent.adapters.toCompactDisplayItems
import com.campus.fitintent.adapters.toProgressDisplayItems
import com.campus.fitintent.databinding.FragmentRewardsBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.BadgeManager
import com.campus.fitintent.utils.PointCalculator
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.RewardsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class RewardsFragment : Fragment() {

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!
    private lateinit var rewardsViewModel: RewardsViewModel

    private lateinit var recentAchievementsAdapter: BadgeAdapter
    private lateinit var nextBadgesAdapter: BadgeAdapter

    private var currentUserId: Long = 1L // TODO: Get from session manager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory.getInstance(app)
        rewardsViewModel = ViewModelProvider(this, factory)[RewardsViewModel::class.java]

        setupUI()
        setupClickListeners()
        observeViewModel()
        loadInitialData()
    }

    private fun setupUI() {
        // Setup Recent Achievements RecyclerView (Horizontal)
        recentAchievementsAdapter = BadgeAdapter(
            viewType = BadgeViewType.COMPACT,
            onBadgeClick = { badge -> showBadgeDetails(badge) }
        )

        binding.recyclerViewRecentAchievements.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentAchievementsAdapter
        }

        // Setup Next Badges RecyclerView (Vertical)
        nextBadgesAdapter = BadgeAdapter(
            viewType = BadgeViewType.PROGRESS,
            onBadgeClick = { badge -> showBadgeDetails(badge) }
        )

        binding.recyclerViewNextBadges.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = nextBadgesAdapter
        }

        // Initialize loading state
        showLoading()
    }

    private fun setupClickListeners() {
        // Leaderboard button
        binding.btnLeaderboard.setOnClickListener {
            showComingSoon("Leaderboard")
        }

        // View all badges button
        binding.btnViewAllBadges.setOnClickListener {
            showAllBadges()
        }

        // Level card click
        binding.cardUserLevel.setOnClickListener {
            showLevelDetails()
        }
    }

    private fun observeViewModel() {
        // Observe loading state
        rewardsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        // Observe user level and points
        rewardsViewModel.userStats.observe(viewLifecycleOwner) { stats ->
            updateUserLevelCard(stats)
            updateQuickStats(stats)
        }

        // Observe recent achievements
        rewardsViewModel.recentAchievements.observe(viewLifecycleOwner) { achievements ->
            if (achievements.isEmpty()) {
                binding.recyclerViewRecentAchievements.visibility = View.GONE
                // Could show an empty state here
            } else {
                binding.recyclerViewRecentAchievements.visibility = View.VISIBLE
                recentAchievementsAdapter.submitList(achievements)
            }
        }

        // Observe upcoming badges
        rewardsViewModel.upcomingBadges.observe(viewLifecycleOwner) { upcomingBadges ->
            if (upcomingBadges.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
                nextBadgesAdapter.submitList(upcomingBadges)
            }
        }

        // Observe errors
        rewardsViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showError(it)
            }
        }
    }

    private fun loadInitialData() {
        rewardsViewModel.loadUserStats(currentUserId)
        rewardsViewModel.loadRecentAchievements(currentUserId)
        rewardsViewModel.loadUpcomingBadges(currentUserId)
    }

    private fun updateUserLevelCard(stats: UserStats) {
        with(binding) {
            // User level and title
            val levelTitle = PointCalculator.getLevelTitle(stats.level)
            tvUserLevel.text = formatUserLevel(stats.level, levelTitle)

            // Total points
            tvTotalPoints.text = formatPoints(stats.totalPoints)

            // Progress to next level
            val pointsToNext = PointCalculator.calculatePointsToNextLevel(stats.totalPoints)
            tvPointsToNextLevel.text = formatPointsToGo(pointsToNext)

            // Progress bar
            val progressPercent = (PointCalculator.calculateLevelProgress(stats.totalPoints) * 100).toInt()
            progressLevel.progress = progressPercent
        }
    }

    private fun updateQuickStats(stats: UserStats) {
        with(binding) {
            // Badges earned
            tvBadgesEarned.text = stats.badgesEarned.toString()

            // Current streak
            tvCurrentStreak.text = stats.currentStreak.toString()

            // Completion rate
            tvCompletionRate.text = formatPercentageDisplay(stats.completionRate.toInt())
        }
    }

    private fun showBadgeDetails(badge: Badge) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(badge.name)
            .setMessage(buildBadgeDetailsMessage(badge))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }

    private fun buildBadgeDetailsMessage(badge: Badge): String {
        val points = PointCalculator.calculateBadgePoints(badge)
        return buildString {
            append(badge.description)
            append("\n\n")
            append("Rarity: ${badge.rarity.name}")
            append("\n")
            append("Reward: $points points")
            append("\n")
            append("Target: ${badge.targetProgress}")
        }
    }

    private fun showAllBadges() {
        // TODO: Navigate to all badges screen or show in dialog
        Snackbar.make(binding.root, "All badges screen coming soon!", Snackbar.LENGTH_SHORT).show()
    }

    private fun showLevelDetails() {
        val stats = rewardsViewModel.userStats.value ?: return

        val message = buildString {
            append("Level ${stats.level}: ${PointCalculator.getLevelTitle(stats.level)}\n\n")
            append("Total Points: ${formatPoints(stats.totalPoints)}\n")
            append("Points to Next Level: ${PointCalculator.calculatePointsToNextLevel(stats.totalPoints)}\n")
            append("Badges Earned: ${stats.badgesEarned}\n")
            append("Current Streak: ${stats.currentStreak} days\n")
            append("Completion Rate: ${stats.completionRate.toInt()}%")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Your Progress")
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }

    private fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE
        binding.recyclerViewRecentAchievements.visibility = View.GONE
        binding.recyclerViewNextBadges.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressLoading.visibility = View.GONE
    }

    private fun showEmptyState() {
        binding.layoutEmptyState.visibility = View.VISIBLE
        binding.recyclerViewNextBadges.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.layoutEmptyState.visibility = View.GONE
        binding.recyclerViewNextBadges.visibility = View.VISIBLE
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                loadInitialData()
            }
            .show()
    }

    private fun showComingSoon(feature: String) {
        Snackbar.make(binding.root, "$feature coming soon!", Snackbar.LENGTH_SHORT).show()
    }

    private fun formatPoints(points: Int): String {
        return when {
            points >= 1000000 -> String.format("%.1fM", points / 1000000.0)
            points >= 1000 -> String.format("%.1fK", points / 1000.0)
            else -> points.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/**
 * Data class for user statistics display
 */
data class UserStats(
    val level: Int,
    val totalPoints: Int,
    val badgesEarned: Int,
    val currentStreak: Int,
    val completionRate: Float
)

/**
 * Extension functions for custom string formatting
 */
private fun Fragment.formatUserLevel(level: Int, title: String): String {
    return "$level - $title"
}

private fun Fragment.formatPointsToGo(points: Int): String {
    return "$points points to go"
}

private fun Fragment.formatPercentageDisplay(percentage: Int): String {
    return "$percentage%"
}
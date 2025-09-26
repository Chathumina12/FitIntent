package com.campus.fitintent.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.campus.fitintent.R
import com.campus.fitintent.activities.AuthActivity
import com.campus.fitintent.activities.EditProfileActivity
import com.campus.fitintent.activities.NotificationSettingsActivity
import com.campus.fitintent.activities.OnboardingQuizActivity
import com.campus.fitintent.databinding.FragmentProfileBinding
import com.campus.fitintent.models.FitnessGoal
import com.campus.fitintent.utils.DateUtils
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.Result
import com.campus.fitintent.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch
import java.io.File

/**
 * Profile Fragment for displaying user information, progress stats, and settings
 * Follows Material Design 3 dark theme with red accents
 */
class ProfileFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var preferencesManager: PreferencesManager

    private var isQuizContentExpanded = false

    // Profile image picker
    private val profileImagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateProfileImage(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesManager = PreferencesManager(requireContext())

        setupUI()
        setupClickListeners()
        observeViewModel()

        // Load initial data
        viewModel.loadUserProfile()
        viewModel.loadProgressStats()
    }

    private fun setupUI() {
        // Setup pull-to-refresh (if wrapping in SwipeRefreshLayout)
        // For now, we'll implement manual refresh

        // Initial quiz content state
        binding.layoutQuizContent.visibility = View.GONE
        binding.imgQuizExpand.setImageResource(R.drawable.ic_expand_more)
    }

    private fun setupClickListeners() {
        // Profile editing
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.imgUserAvatar.setOnClickListener {
            showProfileImageOptions()
        }

        // Quiz section expand/collapse
        binding.layoutQuizHeader.setOnClickListener {
            toggleQuizContent()
        }

        binding.btnEditPreferences.setOnClickListener {
            startActivity(Intent(requireContext(), OnboardingQuizActivity::class.java))
        }

        // Settings navigation
        binding.layoutNotifications.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationSettingsActivity::class.java))
        }

        binding.layoutTheme.setOnClickListener {
            showThemeSelector()
        }

        binding.layoutDataExport.setOnClickListener {
            viewModel.exportUserData()
        }

        binding.layoutAbout.setOnClickListener {
            showAboutDialog()
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        // Progress cards click for detailed views
        binding.cardTotalWorkouts.setOnClickListener {
            // Navigate to workout history
            Toast.makeText(context, "Navigate to Workout History", Toast.LENGTH_SHORT).show()
        }

        binding.cardCurrentStreak.setOnClickListener {
            // Navigate to streak details
            Toast.makeText(context, "Navigate to Streak Details", Toast.LENGTH_SHORT).show()
        }

        binding.cardPointsEarned.setOnClickListener {
            // Navigate to rewards/badges
            Toast.makeText(context, "Navigate to Rewards", Toast.LENGTH_SHORT).show()
        }

        binding.cardDaysActive.setOnClickListener {
            // Navigate to activity calendar
            Toast.makeText(context, "Navigate to Activity Calendar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        // Observe user profile
        viewModel.userProfile.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading state if needed
                }
                is Result.Success -> {
                    val user = result.data
                    updateUserUI(user)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed to load profile: ${result.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Observe progress stats
        viewModel.progressStats.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading state
                }
                is Result.Success -> {
                    val stats = result.data
                    updateProgressUI(stats)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed to load progress: ${result.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Observe profile image updates
        viewModel.profileImageUpdate.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator
                }
                is Result.Success -> {
                    Toast.makeText(context, "Profile picture updated", Toast.LENGTH_SHORT).show()
                    // Reload profile to get updated image path
                    viewModel.loadUserProfile()
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed to update profile picture", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe data export
        viewModel.dataExportResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(context, "Preparing data export...", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    shareExportedData(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed to export data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe logout
        viewModel.logoutResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    // Clear preferences and navigate to auth
                    preferencesManager.clearSession()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed to logout", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    // Show loading if needed
                }
            }
        }
    }

    private fun updateUserUI(user: com.campus.fitintent.models.User) {
        with(binding) {
            tvUserName.text = user.fullName.ifEmpty { user.username }
            tvUserEmail.text = user.email
            tvMemberSince.text = getString(R.string.member_since_format,
                DateUtils.formatDateShort(user.createdAt))

            // Load profile image
            if (!user.profileImagePath.isNullOrEmpty() && File(user.profileImagePath).exists()) {
                Glide.with(this@ProfileFragment)
                    .load(user.profileImagePath)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(imgUserAvatar)
            } else {
                imgUserAvatar.setImageResource(R.drawable.ic_person)
            }

            // Update quiz results
            updateQuizResultsUI(user)
        }
    }

    private fun updateQuizResultsUI(user: com.campus.fitintent.models.User) {
        with(binding) {
            // Primary goal
            tvPrimaryGoal.text = when (user.primaryGoal) {
                FitnessGoal.LOSE_WEIGHT -> getString(R.string.goal_lose_weight)
                FitnessGoal.GAIN_MUSCLE -> getString(R.string.goal_gain_muscle)
                FitnessGoal.IMPROVE_ENDURANCE -> getString(R.string.goal_improve_endurance)
                FitnessGoal.MAINTAIN_FITNESS -> getString(R.string.goal_maintain_fitness)
                FitnessGoal.INCREASE_FLEXIBILITY -> getString(R.string.goal_increase_flexibility)
                FitnessGoal.GENERAL_HEALTH -> getString(R.string.goal_general_health)
                null -> getString(R.string.not_set)
            }

            // Fitness level
            tvFitnessLevel.text = when (user.activityLevel) {
                com.campus.fitintent.models.ActivityLevel.SEDENTARY -> getString(R.string.level_beginner)
                com.campus.fitintent.models.ActivityLevel.LIGHTLY_ACTIVE -> getString(R.string.level_beginner)
                com.campus.fitintent.models.ActivityLevel.MODERATELY_ACTIVE -> getString(R.string.level_intermediate)
                com.campus.fitintent.models.ActivityLevel.VERY_ACTIVE -> getString(R.string.level_advanced)
                com.campus.fitintent.models.ActivityLevel.EXTRA_ACTIVE -> getString(R.string.level_advanced)
                null -> getString(R.string.not_set)
            }

            // These would come from additional onboarding data
            tvPreferredWorkout.text = getString(R.string.not_set)
            tvTimeAvailability.text = getString(R.string.not_set)
        }
    }

    private fun updateProgressUI(stats: ProfileViewModel.ProgressStats) {
        with(binding) {
            tvTotalWorkouts.text = stats.totalWorkouts.toString()
            tvCurrentStreak.text = stats.currentStreak.toString()
            tvPointsEarned.text = formatPoints(stats.totalPoints)
            tvDaysActive.text = stats.daysActive.toString()
        }
    }

    private fun formatPoints(points: Int): String {
        return when {
            points >= 1000 -> String.format("%.1fK", points / 1000.0)
            else -> points.toString()
        }
    }

    private fun toggleQuizContent() {
        isQuizContentExpanded = !isQuizContentExpanded

        with(binding) {
            if (isQuizContentExpanded) {
                layoutQuizContent.visibility = View.VISIBLE
                imgQuizExpand.setImageResource(R.drawable.ic_expand_less)
            } else {
                layoutQuizContent.visibility = View.GONE
                imgQuizExpand.setImageResource(R.drawable.ic_expand_more)
            }
        }
    }

    private fun showProfileImageOptions() {
        val options = arrayOf(
            getString(R.string.choose_from_gallery),
            getString(R.string.remove_picture)
        )

        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.profile_picture))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> profileImagePicker.launch("image/*")
                    1 -> viewModel.removeProfileImage()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showThemeSelector() {
        val themes = arrayOf(
            getString(R.string.theme_dark),
            getString(R.string.theme_light),
            getString(R.string.theme_system)
        )

        val currentTheme = preferencesManager.getTheme()

        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.choose_theme))
            .setSingleChoiceItems(themes, currentTheme) { dialog, which ->
                preferencesManager.setTheme(which)
                binding.tvCurrentTheme.text = themes[which]
                dialog.dismiss()

                // Restart activity to apply theme
                requireActivity().recreate()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.about_fitintent))
            .setMessage(getString(R.string.about_app_description))
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialog_Dark)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_confirmation))
            .setPositiveButton(getString(R.string.logout)) { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun shareExportedData(file: File) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, androidx.core.content.FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            ))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.fitintent_data_export))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_data_export)))
    }

    override fun onRefresh() {
        // Implement pull-to-refresh if SwipeRefreshLayout is added
        viewModel.loadUserProfile()
        viewModel.loadProgressStats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
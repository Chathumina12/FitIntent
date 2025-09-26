package com.campus.fitintent.fragments

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.activities.AuthActivity
import com.campus.fitintent.databinding.FragmentSettingsBinding
import com.campus.fitintent.utils.FitIntentNotificationManager
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.ReminderScheduler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var reminderScheduler: ReminderScheduler
    private lateinit var notificationManager: FitIntentNotificationManager

    // Theme options
    private val themeOptions = arrayOf("System Default", "Light", "Dark")
    private val themeValues = arrayOf("system", "light", "dark")

    // Unit options
    private val unitOptions = arrayOf("Metric (kg, cm)", "Imperial (lbs, ft)")
    private val unitValues = arrayOf("metric", "imperial")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reminderScheduler = ReminderScheduler(requireContext())
        notificationManager = FitIntentNotificationManager(requireContext())

        setupClickListeners()
        loadSettings()
    }

    private fun setupClickListeners() {
        with(binding) {
            // Daily reminders switch
            switchDailyReminders.setOnCheckedChangeListener { _, isChecked ->
                handleDailyRemindersToggle(isChecked)
            }

            // Daily reminder time picker
            layoutDailyReminderTime.setOnClickListener {
                showTimePickerDialog()
            }

            // Achievement notifications switch
            switchAchievementNotifications.setOnCheckedChangeListener { _, isChecked ->
                PreferencesManager.setAchievementNotificationsEnabled(requireContext(), isChecked)
                showSettingsSaved()
            }

            // Streak alerts switch
            switchStreakAlerts.setOnCheckedChangeListener { _, isChecked ->
                PreferencesManager.setStreakAlertsEnabled(requireContext(), isChecked)
                if (isChecked) {
                    // Schedule streak protection alerts
                    reminderScheduler.scheduleStreakProtectionAlert(
                        PreferencesManager.getCurrentStreak(requireContext()),
                        3
                    )
                } else {
                    reminderScheduler.cancelStreakProtectionAlert()
                }
                showSettingsSaved()
            }

            // Theme selection
            layoutThemeSelection.setOnClickListener {
                showThemeSelectionDialog()
            }

            // Units preference
            layoutUnitsPreference.setOnClickListener {
                showUnitsSelectionDialog()
            }

            // Export data
            layoutExportData.setOnClickListener {
                showExportDataConfirmation()
            }

            // Clear data
            layoutClearData.setOnClickListener {
                showClearDataConfirmation()
            }

            // About app
            layoutAboutApp.setOnClickListener {
                showAboutDialog()
            }

            // Logout button
            btnLogout.setOnClickListener {
                showLogoutConfirmation()
            }
        }
    }

    private fun loadSettings() {
        with(binding) {
            // Load notification settings
            switchDailyReminders.isChecked = PreferencesManager.isDailyReminderEnabled(requireContext())
            switchAchievementNotifications.isChecked =
                PreferencesManager.areAchievementNotificationsEnabled(requireContext())
            switchStreakAlerts.isChecked = PreferencesManager.areStreakAlertsEnabled(requireContext())

            // Load reminder time
            val (hour, minute) = PreferencesManager.getDailyReminderTime(requireContext())
            updateReminderTimeDisplay(hour, minute)

            // Load theme
            val currentTheme = PreferencesManager.getThemePreference(requireContext())
            updateThemeDisplay(currentTheme)

            // Load units
            val currentUnits = PreferencesManager.getUnitsPreference(requireContext())
            updateUnitsDisplay(currentUnits)

            // Enable/disable reminder time based on daily reminders switch
            layoutDailyReminderTime.isEnabled = switchDailyReminders.isChecked
            layoutDailyReminderTime.alpha = if (switchDailyReminders.isChecked) 1.0f else 0.5f
        }
    }

    private fun handleDailyRemindersToggle(isEnabled: Boolean) {
        if (isEnabled) {
            val (hour, minute) = PreferencesManager.getDailyReminderTime(requireContext())
            reminderScheduler.scheduleDailyReminder(hour, minute)
        } else {
            reminderScheduler.cancelDailyReminder()
        }

        // Update UI state
        binding.layoutDailyReminderTime.isEnabled = isEnabled
        binding.layoutDailyReminderTime.alpha = if (isEnabled) 1.0f else 0.5f

        showSettingsSaved()
    }

    private fun showTimePickerDialog() {
        val (currentHour, currentMinute) = PreferencesManager.getDailyReminderTime(requireContext())

        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                // Save new time
                PreferencesManager.setDailyReminderTime(requireContext(), hourOfDay, minute)
                updateReminderTimeDisplay(hourOfDay, minute)

                // Reschedule reminder if enabled
                if (binding.switchDailyReminders.isChecked) {
                    reminderScheduler.scheduleDailyReminder(hourOfDay, minute)
                }

                showSettingsSaved()
            },
            currentHour,
            currentMinute,
            false // 12-hour format
        ).show()
    }

    private fun showThemeSelectionDialog() {
        val currentTheme = PreferencesManager.getThemePreference(requireContext())
        val currentSelection = themeValues.indexOf(currentTheme)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.theme_dialog_title))
            .setSingleChoiceItems(themeOptions, currentSelection) { dialog, which ->
                val selectedTheme = themeValues[which]
                PreferencesManager.setThemePreference(requireContext(), selectedTheme)
                updateThemeDisplay(selectedTheme)
                showSettingsSaved()

                // TODO: Apply theme change immediately
                // AppCompatDelegate.setDefaultNightMode(getThemeMode(selectedTheme))

                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showUnitsSelectionDialog() {
        val currentUnits = PreferencesManager.getUnitsPreference(requireContext())
        val currentSelection = unitValues.indexOf(currentUnits)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.units_dialog_title))
            .setSingleChoiceItems(unitOptions, currentSelection) { dialog, which ->
                val selectedUnits = unitValues[which]
                PreferencesManager.setUnitsPreference(requireContext(), selectedUnits)
                updateUnitsDisplay(selectedUnits)
                showSettingsSaved()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showExportDataConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.export_data))
            .setMessage(getString(R.string.export_data_desc))
            .setPositiveButton(getString(R.string.export)) { _, _ ->
                exportUserData()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showClearDataConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.clear_data_confirm_title))
            .setMessage(getString(R.string.clear_data_confirm_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                clearAllData()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showAboutDialog() {
        val aboutMessage = buildString {
            append(getString(R.string.about_app_description))
            append("\n\n")
            append("Version: 1.0.0\n")
            append("Build: ${System.currentTimeMillis()}\n")
            append("Platform: Android\n")
            append("\n")
            append("Developed with ❤️ for healthy habits")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.about_fitintent))
            .setMessage(aboutMessage)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.logout_confirm_title))
            .setMessage(getString(R.string.logout_confirm_message))
            .setPositiveButton(getString(R.string.logout)) { _, _ ->
                performLogout()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun exportUserData() {
        lifecycleScope.launch {
            try {
                Snackbar.make(binding.root, getString(R.string.data_export_started), Snackbar.LENGTH_SHORT).show()

                withContext(Dispatchers.IO) {
                    // TODO: Implement actual data export
                    val app = requireActivity().application as FitIntentApplication

                    // Get all user data
                    // val userData = app.userRepository.exportUserData()
                    // val habitData = app.habitRepository.exportHabitData()
                    // val workoutData = app.workoutRepository.exportWorkoutData()
                    // val progressData = app.progressRepository.exportProgressData()

                    // Create export file
                    // FileExporter.createExportFile(userData, habitData, workoutData, progressData)

                    // Simulate export process
                    Thread.sleep(2000)
                }

                Snackbar.make(binding.root, getString(R.string.data_export_completed), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.share_data_export)) {
                        // TODO: Share the exported file
                    }
                    .show()

            } catch (e: Exception) {
                Snackbar.make(binding.root, "Export failed: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun clearAllData() {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val app = requireActivity().application as FitIntentApplication

                    // Clear all repositories
                    app.database.clearAllTables()

                    // Clear preferences
                    PreferencesManager.clearAllPreferences(requireContext())

                    // Cancel all reminders
                    reminderScheduler.cancelAllReminders()

                    // Clear all notifications
                    notificationManager.cancelAllNotifications()
                }

                Snackbar.make(binding.root, getString(R.string.data_cleared), Snackbar.LENGTH_SHORT).show()

                // Navigate to auth screen
                performLogout()

            } catch (e: Exception) {
                Snackbar.make(binding.root, "Clear data failed: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun performLogout() {
        // Clear user session
        PreferencesManager.clearUserSession(requireContext())

        // Cancel all reminders
        reminderScheduler.cancelAllReminders()

        // Navigate to auth screen
        val intent = Intent(requireContext(), AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun updateReminderTimeDisplay(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        binding.tvDailyReminderTime.text = timeFormat.format(calendar.time)
    }

    private fun updateThemeDisplay(theme: String) {
        val displayText = when (theme) {
            "light" -> getString(R.string.theme_light)
            "dark" -> getString(R.string.theme_dark)
            else -> getString(R.string.theme_system)
        }
        binding.tvCurrentTheme.text = displayText
    }

    private fun updateUnitsDisplay(units: String) {
        val displayText = if (units == "imperial") {
            getString(R.string.imperial_units)
        } else {
            getString(R.string.metric_units)
        }
        binding.tvCurrentUnits.text = displayText
    }

    private fun showSettingsSaved() {
        Snackbar.make(binding.root, getString(R.string.settings_saved), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/**
 * Extension functions for PreferencesManager settings
 * These extensions provide additional functionality beyond the base PreferencesManager
 */

// Daily Reminder Time (Additional functionality not in base PreferencesManager)
fun PreferencesManager.Companion.getDailyReminderTime(context: android.content.Context): Pair<Int, Int> {
    val prefsManager = PreferencesManager(context)
    // Using a custom approach to get time as hours/minutes since base manager uses string format
    val timeString = prefsManager.getReminderTime() // Returns "HH:MM" format
    val parts = timeString.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: 9
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    return Pair(hour, minute)
}

fun PreferencesManager.Companion.setDailyReminderTime(context: android.content.Context, hour: Int, minute: Int) {
    val prefsManager = PreferencesManager(context)
    val timeString = String.format("%02d:%02d", hour, minute)
    prefsManager.setReminderTime(timeString)
}

// Clear All Preferences (Additional functionality)
fun PreferencesManager.Companion.clearAllPreferences(context: android.content.Context) {
    val prefsManager = PreferencesManager(context)
    prefsManager.clearSession() // Use existing clearSession method
}

// Clear User Session (Additional functionality)
fun PreferencesManager.Companion.clearUserSession(context: android.content.Context) {
    val prefsManager = PreferencesManager(context)
    prefsManager.clearUserSession() // Use existing method
}
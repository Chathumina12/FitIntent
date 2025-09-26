package com.campus.fitintent.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityNotificationSettingsBinding

/**
 * Activity for managing notification settings
 * Minimal stub implementation for compilation
 */
class NotificationSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupClickListeners()
        loadNotificationSettings()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Notification Settings"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupClickListeners() {
        binding.switchWorkoutReminders.setOnCheckedChangeListener { _, isChecked ->
            saveWorkoutReminders(isChecked)
        }

        binding.switchHabitReminders.setOnCheckedChangeListener { _, isChecked ->
            saveHabitReminders(isChecked)
        }

        binding.switchProgressUpdates.setOnCheckedChangeListener { _, isChecked ->
            saveProgressUpdates(isChecked)
        }

        binding.switchDailyMotivation.setOnCheckedChangeListener { _, isChecked ->
            saveDailyMotivation(isChecked)
        }

        binding.btnTestNotification.setOnClickListener {
            testNotification()
        }
    }

    private fun loadNotificationSettings() {
        // TODO: Load settings from preferences
        // For now, set default values
        binding.switchWorkoutReminders.isChecked = true
        binding.switchHabitReminders.isChecked = true
        binding.switchProgressUpdates.isChecked = false
        binding.switchDailyMotivation.isChecked = true
    }

    private fun saveWorkoutReminders(enabled: Boolean) {
        // TODO: Save to preferences
        showSettingSaved("Workout reminders")
    }

    private fun saveHabitReminders(enabled: Boolean) {
        // TODO: Save to preferences
        showSettingSaved("Habit reminders")
    }

    private fun saveProgressUpdates(enabled: Boolean) {
        // TODO: Save to preferences
        showSettingSaved("Progress updates")
    }

    private fun saveDailyMotivation(enabled: Boolean) {
        // TODO: Save to preferences
        showSettingSaved("Daily motivation")
    }

    private fun testNotification() {
        // TODO: Send test notification
        Toast.makeText(this, "Test notification sent", Toast.LENGTH_SHORT).show()
    }

    private fun showSettingSaved(settingName: String) {
        Toast.makeText(this, "$settingName setting saved", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
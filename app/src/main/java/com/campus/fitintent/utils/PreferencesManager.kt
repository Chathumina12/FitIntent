package com.campus.fitintent.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Manages encrypted shared preferences for secure data storage
 */
class PreferencesManager(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // User session management
    fun saveUserId(userId: Long) {
        encryptedPrefs.edit().putLong(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): Long {
        return encryptedPrefs.getLong(KEY_USER_ID, -1L)
    }

    fun saveUserEmail(email: String) {
        encryptedPrefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return encryptedPrefs.getString(KEY_USER_EMAIL, null)
    }

    fun setOnboardingComplete(complete: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_ONBOARDING_COMPLETE, complete).apply()
    }

    fun isOnboardingComplete(): Boolean {
        return encryptedPrefs.getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

    fun clearUserSession() {
        encryptedPrefs.edit().apply {
            remove(KEY_USER_ID)
            remove(KEY_USER_EMAIL)
            remove(KEY_ONBOARDING_COMPLETE)
            apply()
        }
    }

<<<<<<< HEAD
=======
    fun clearSession() {
        clearUserSession()
    }

    fun getTheme(): Int {
        return encryptedPrefs.getInt(KEY_THEME, 0) // 0 = dark, 1 = light, 2 = system
    }

    fun setTheme(theme: Int) {
        encryptedPrefs.edit().putInt(KEY_THEME, theme).apply()
    }

>>>>>>> 818ab1f (Updated)
    // App settings
    fun setNotificationsEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply()
    }

    fun areNotificationsEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun isDarkModeEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setReminderTime(time: String) {
        encryptedPrefs.edit().putString(KEY_REMINDER_TIME, time).apply()
    }

    fun getReminderTime(): String {
        return encryptedPrefs.getString(KEY_REMINDER_TIME, "08:00") ?: "08:00"
    }

    // Fitness preferences
    fun setPreferredUnits(metric: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_METRIC_UNITS, metric).apply()
    }

    fun useMetricUnits(): Boolean {
        return encryptedPrefs.getBoolean(KEY_METRIC_UNITS, true)
    }

    fun setDailyGoals(
        calories: Int? = null,
        steps: Int? = null,
        water: Int? = null,
        sleepHours: Float? = null,
        workoutMinutes: Int? = null
    ) {
        encryptedPrefs.edit().apply {
            calories?.let { putInt(KEY_DAILY_CALORIE_GOAL, it) }
            steps?.let { putInt(KEY_DAILY_STEP_GOAL, it) }
            water?.let { putInt(KEY_DAILY_WATER_GOAL, it) }
            sleepHours?.let { putFloat(KEY_DAILY_SLEEP_GOAL, it) }
            workoutMinutes?.let { putInt(KEY_DAILY_WORKOUT_GOAL, it) }
            apply()
        }
    }

    fun getDailyCalorieGoal(): Int {
        return encryptedPrefs.getInt(KEY_DAILY_CALORIE_GOAL, 2000)
    }

    fun getDailyStepGoal(): Int {
        return encryptedPrefs.getInt(KEY_DAILY_STEP_GOAL, 10000)
    }

    fun getDailyWaterGoal(): Int {
        return encryptedPrefs.getInt(KEY_DAILY_WATER_GOAL, 2000) // ml
    }

    fun getDailySleepGoal(): Float {
        return encryptedPrefs.getFloat(KEY_DAILY_SLEEP_GOAL, 8f) // hours
    }

    fun getDailyWorkoutGoal(): Int {
        return encryptedPrefs.getInt(KEY_DAILY_WORKOUT_GOAL, 30) // minutes
    }

    // App statistics
    fun incrementAppOpenCount() {
        val current = encryptedPrefs.getInt(KEY_APP_OPEN_COUNT, 0)
        encryptedPrefs.edit().putInt(KEY_APP_OPEN_COUNT, current + 1).apply()
    }

    fun getAppOpenCount(): Int {
        return encryptedPrefs.getInt(KEY_APP_OPEN_COUNT, 0)
    }

    fun isFirstLaunch(): Boolean {
        val isFirst = encryptedPrefs.getBoolean(KEY_FIRST_LAUNCH, true)
        if (isFirst) {
            // Mark as no longer first launch
            encryptedPrefs.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
        return isFirst
    }

    fun setLastOpenDate(date: Long) {
        encryptedPrefs.edit().putLong(KEY_LAST_OPEN_DATE, date).apply()
    }

    fun getLastOpenDate(): Long {
        return encryptedPrefs.getLong(KEY_LAST_OPEN_DATE, 0L)
    }

    // Feature flags
    fun setFeatureEnabled(feature: String, enabled: Boolean) {
        encryptedPrefs.edit().putBoolean("feature_$feature", enabled).apply()
    }

    fun isFeatureEnabled(feature: String, defaultValue: Boolean = false): Boolean {
        return encryptedPrefs.getBoolean("feature_$feature", defaultValue)
    }

<<<<<<< HEAD
=======
    // Notification settings
    fun setAchievementNotificationsEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_ACHIEVEMENT_NOTIFICATIONS, enabled).apply()
    }

    fun areAchievementNotificationsEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_ACHIEVEMENT_NOTIFICATIONS, true)
    }

    fun setStreakAlertsEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_STREAK_ALERTS, enabled).apply()
    }

    fun areStreakAlertsEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_STREAK_ALERTS, true)
    }

    fun setCurrentStreak(streak: Int) {
        encryptedPrefs.edit().putInt(KEY_CURRENT_STREAK, streak).apply()
    }

    fun getCurrentStreak(): Int {
        return encryptedPrefs.getInt(KEY_CURRENT_STREAK, 0)
    }

    fun setThemePreference(theme: String) {
        encryptedPrefs.edit().putString(KEY_THEME_PREFERENCE, theme).apply()
    }

    fun getThemePreference(): String {
        return encryptedPrefs.getString(KEY_THEME_PREFERENCE, "system") ?: "system"
    }

    fun setUnitsPreference(units: String) {
        encryptedPrefs.edit().putString(KEY_UNITS_PREFERENCE, units).apply()
    }

    fun getUnitsPreference(): String {
        return encryptedPrefs.getString(KEY_UNITS_PREFERENCE, "metric") ?: "metric"
    }

    fun setDailyReminderEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_DAILY_REMINDER_ENABLED, enabled).apply()
    }

    fun isDailyReminderEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_DAILY_REMINDER_ENABLED, true)
    }

>>>>>>> 818ab1f (Updated)
    companion object {
        private const val PREFS_NAME = "fitintent_secure_prefs"

        // Keys for user session
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_ONBOARDING_COMPLETE = "onboarding_complete"

        // Keys for app settings
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_DARK_MODE = "dark_mode"
<<<<<<< HEAD
        private const val KEY_REMINDER_TIME = "reminder_time"
        private const val KEY_METRIC_UNITS = "metric_units"
=======
        private const val KEY_THEME = "theme"
        private const val KEY_REMINDER_TIME = "reminder_time"
        private const val KEY_METRIC_UNITS = "metric_units"
        private const val KEY_ACHIEVEMENT_NOTIFICATIONS = "achievement_notifications_enabled"
        private const val KEY_STREAK_ALERTS = "streak_alerts_enabled"
        private const val KEY_CURRENT_STREAK = "current_streak"
        private const val KEY_THEME_PREFERENCE = "theme_preference"
        private const val KEY_UNITS_PREFERENCE = "units_preference"
        private const val KEY_DAILY_REMINDER_ENABLED = "daily_reminder_enabled"
>>>>>>> 818ab1f (Updated)

        // Keys for daily goals
        private const val KEY_DAILY_CALORIE_GOAL = "daily_calorie_goal"
        private const val KEY_DAILY_STEP_GOAL = "daily_step_goal"
        private const val KEY_DAILY_WATER_GOAL = "daily_water_goal"
        private const val KEY_DAILY_SLEEP_GOAL = "daily_sleep_goal"
        private const val KEY_DAILY_WORKOUT_GOAL = "daily_workout_goal"

        // Keys for app statistics
        private const val KEY_APP_OPEN_COUNT = "app_open_count"
        private const val KEY_LAST_OPEN_DATE = "last_open_date"
        private const val KEY_FIRST_LAUNCH = "first_launch"
<<<<<<< HEAD
=======

        // Static convenience methods for settings that need Context
        @JvmStatic
        fun setAchievementNotificationsEnabled(context: Context, enabled: Boolean) {
            PreferencesManager(context).setAchievementNotificationsEnabled(enabled)
        }

        @JvmStatic
        fun areAchievementNotificationsEnabled(context: Context): Boolean {
            return PreferencesManager(context).areAchievementNotificationsEnabled()
        }

        @JvmStatic
        fun setStreakAlertsEnabled(context: Context, enabled: Boolean) {
            PreferencesManager(context).setStreakAlertsEnabled(enabled)
        }

        @JvmStatic
        fun areStreakAlertsEnabled(context: Context): Boolean {
            return PreferencesManager(context).areStreakAlertsEnabled()
        }

        @JvmStatic
        fun getCurrentStreak(context: Context): Int {
            return PreferencesManager(context).getCurrentStreak()
        }

        @JvmStatic
        fun setCurrentStreak(context: Context, streak: Int) {
            PreferencesManager(context).setCurrentStreak(streak)
        }

        @JvmStatic
        fun getThemePreference(context: Context): String {
            return PreferencesManager(context).getThemePreference()
        }

        @JvmStatic
        fun setThemePreference(context: Context, theme: String) {
            PreferencesManager(context).setThemePreference(theme)
        }

        @JvmStatic
        fun getUnitsPreference(context: Context): String {
            return PreferencesManager(context).getUnitsPreference()
        }

        @JvmStatic
        fun setUnitsPreference(context: Context, units: String) {
            PreferencesManager(context).setUnitsPreference(units)
        }

        @JvmStatic
        fun isDailyReminderEnabled(context: Context): Boolean {
            return PreferencesManager(context).isDailyReminderEnabled()
        }

        @JvmStatic
        fun setDailyReminderEnabled(context: Context, enabled: Boolean) {
            PreferencesManager(context).setDailyReminderEnabled(enabled)
        }

        @JvmStatic
        fun setDailyReminderTime(context: Context, hour: Int, minute: Int) {
            val timeString = String.format("%02d:%02d", hour, minute)
            PreferencesManager(context).setReminderTime(timeString)
        }

        @JvmStatic
        fun getDailyReminderTime(context: Context): Pair<Int, Int> {
            val timeString = PreferencesManager(context).getReminderTime()
            val parts = timeString.split(":")
            return if (parts.size == 2) {
                try {
                    Pair(parts[0].toInt(), parts[1].toInt())
                } catch (e: NumberFormatException) {
                    Pair(8, 0) // Default to 8:00 AM
                }
            } else {
                Pair(8, 0) // Default to 8:00 AM
            }
        }
>>>>>>> 818ab1f (Updated)
    }
}
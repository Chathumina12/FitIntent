package com.campus.fitintent.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * Manages all reminder scheduling using AlarmManager
 * Handles daily reminders, habit reminders, streak alerts, and workout reminders
 */
class ReminderScheduler(private val context: Context) {

    companion object {
        // Alarm Request Codes
        private const val REQUEST_CODE_DAILY_REMINDER = 1000
        private const val REQUEST_CODE_HABIT_BASE = 2000
        private const val REQUEST_CODE_STREAK_PROTECTION = 3000
        private const val REQUEST_CODE_WORKOUT_REMINDER = 4000

        // Intent Actions
        const val ACTION_DAILY_REMINDER = "com.campus.fitintent.DAILY_REMINDER"
        const val ACTION_HABIT_REMINDER = "com.campus.fitintent.HABIT_REMINDER"
        const val ACTION_STREAK_PROTECTION = "com.campus.fitintent.STREAK_PROTECTION"
        const val ACTION_WORKOUT_REMINDER = "com.campus.fitintent.WORKOUT_REMINDER"

        // Extra Keys
        const val EXTRA_REMINDER_TYPE = "reminder_type"
        const val EXTRA_HABIT_ID = "habit_id"
        const val EXTRA_HABIT_NAME = "habit_name"
        const val EXTRA_STREAK_DAYS = "streak_days"
        const val EXTRA_HOURS_LEFT = "hours_left"
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val notificationManager = FitIntentNotificationManager(context)

    /**
     * Schedule daily motivation reminder
     */
    fun scheduleDailyReminder(hour: Int = 9, minute: Int = 0) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_DAILY_REMINDER
            putExtra(EXTRA_REMINDER_TYPE, "daily")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DAILY_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            // If time has passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // Schedule repeating daily alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        PreferencesManager.setDailyReminderTime(context, hour, minute)
        PreferencesManager.setDailyReminderEnabled(context, true)
    }

    /**
     * Schedule habit-specific reminder
     */
    fun scheduleHabitReminder(
        habitId: Long,
        habitName: String,
        hour: Int,
        minute: Int,
        daysOfWeek: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7) // All days by default
    ) {
        daysOfWeek.forEach { dayOfWeek ->
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                action = ACTION_HABIT_REMINDER
                putExtra(EXTRA_REMINDER_TYPE, "habit")
                putExtra(EXTRA_HABIT_ID, habitId)
                putExtra(EXTRA_HABIT_NAME, habitName)
            }

            val requestCode = REQUEST_CODE_HABIT_BASE + (habitId * 10 + dayOfWeek).toInt()
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.DAY_OF_WEEK, dayOfWeek)

                // If time has passed this week, schedule for next week
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7, // Weekly
                    pendingIntent
                )
            }
        }
    }

    /**
     * Schedule streak protection alert (e.g., 3 hours before day ends)
     */
    fun scheduleStreakProtectionAlert(streakDays: Int, hoursBeforeMidnight: Int = 3) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_STREAK_PROTECTION
            putExtra(EXTRA_REMINDER_TYPE, "streak_protection")
            putExtra(EXTRA_STREAK_DAYS, streakDays)
            putExtra(EXTRA_HOURS_LEFT, hoursBeforeMidnight)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_STREAK_PROTECTION,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 24 - hoursBeforeMidnight)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            // If time has passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    /**
     * Schedule workout reminder
     */
    fun scheduleWorkoutReminder(hour: Int, minute: Int, daysOfWeek: List<Int>) {
        daysOfWeek.forEach { dayOfWeek ->
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                action = ACTION_WORKOUT_REMINDER
                putExtra(EXTRA_REMINDER_TYPE, "workout")
            }

            val requestCode = REQUEST_CODE_WORKOUT_REMINDER + dayOfWeek
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.DAY_OF_WEEK, dayOfWeek)

                // If time has passed this week, schedule for next week
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7, // Weekly
                    pendingIntent
                )
            }
        }
    }

    /**
     * Cancel daily reminder
     */
    fun cancelDailyReminder() {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_DAILY_REMINDER
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DAILY_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        PreferencesManager.setDailyReminderEnabled(context, false)
    }

    /**
     * Cancel habit reminder
     */
    fun cancelHabitReminder(habitId: Long) {
        // Cancel all days for this habit
        for (day in 1..7) {
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                action = ACTION_HABIT_REMINDER
                putExtra(EXTRA_HABIT_ID, habitId)
            }

            val requestCode = REQUEST_CODE_HABIT_BASE + (habitId * 10 + day).toInt()
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
        }
    }

    /**
     * Cancel streak protection alert
     */
    fun cancelStreakProtectionAlert() {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_STREAK_PROTECTION
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_STREAK_PROTECTION,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }

    /**
     * Cancel workout reminders
     */
    fun cancelWorkoutReminders() {
        for (day in 1..7) {
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                action = ACTION_WORKOUT_REMINDER
            }

            val requestCode = REQUEST_CODE_WORKOUT_REMINDER + day
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
        }
    }

    /**
     * Cancel all reminders
     */
    fun cancelAllReminders() {
        cancelDailyReminder()
        cancelStreakProtectionAlert()
        cancelWorkoutReminders()
        // Note: Habit reminders need to be canceled individually
    }

    /**
     * Reschedule daily reminders (called after device reboot)
     */
    fun rescheduleReminders() {
        // Reschedule daily reminder if enabled
        if (PreferencesManager.isDailyReminderEnabled(context)) {
            val (hour, minute) = PreferencesManager.getDailyReminderTime(context)
            scheduleDailyReminder(hour, minute)
        }

        // TODO: Reschedule habit and workout reminders
        // This would require storing reminder preferences in SharedPreferences
    }
}

/**
 * BroadcastReceiver that handles all reminder alarms
 */
class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = FitIntentNotificationManager(context)

        when (intent.action) {
            ReminderScheduler.ACTION_DAILY_REMINDER -> {
                handleDailyReminder(context, notificationManager)
            }

            ReminderScheduler.ACTION_HABIT_REMINDER -> {
                handleHabitReminder(context, intent, notificationManager)
            }

            ReminderScheduler.ACTION_STREAK_PROTECTION -> {
                handleStreakProtection(context, intent, notificationManager)
            }

            ReminderScheduler.ACTION_WORKOUT_REMINDER -> {
                handleWorkoutReminder(context, notificationManager)
            }

            Intent.ACTION_BOOT_COMPLETED -> {
                // Reschedule all reminders after device reboot
                val scheduler = ReminderScheduler(context)
                scheduler.rescheduleReminders()
            }
        }
    }

    private fun handleDailyReminder(context: Context, notificationManager: FitIntentNotificationManager) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO: Get actual habit count from repository
                val pendingHabits = 3 // Placeholder

                val motivationalMessages = listOf(
                    "Ready to crush your goals today? 💪",
                    "Your future self will thank you! 🌟",
                    "Small steps, big results! 🚀",
                    "Make today count! ✨",
                    "Your journey to greatness continues! 🎯"
                )

                val message = motivationalMessages.random()
                notificationManager.sendDailyReminder(
                    title = "Good morning, champion! ☀️",
                    message = message,
                    habitCount = pendingHabits
                )

                // Reschedule for tomorrow
                val scheduler = ReminderScheduler(context)
                val (hour, minute) = PreferencesManager.getDailyReminderTime(context)
                scheduler.scheduleDailyReminder(hour, minute)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun handleHabitReminder(
        context: Context,
        intent: Intent,
        notificationManager: FitIntentNotificationManager
    ) {
        val habitId = intent.getLongExtra(ReminderScheduler.EXTRA_HABIT_ID, 0)
        val habitName = intent.getStringExtra(ReminderScheduler.EXTRA_HABIT_NAME) ?: "Your Habit"

        if (habitId != 0L) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // TODO: Get actual habit and streak data from repository
                    val habit = createMockHabit(habitId, habitName)
                    val streakDays = 5 // Placeholder

                    notificationManager.sendHabitReminder(habit, streakDays)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun handleStreakProtection(
        context: Context,
        intent: Intent,
        notificationManager: FitIntentNotificationManager
    ) {
        val streakDays = intent.getIntExtra(ReminderScheduler.EXTRA_STREAK_DAYS, 0)
        val hoursLeft = intent.getIntExtra(ReminderScheduler.EXTRA_HOURS_LEFT, 3)

        if (streakDays > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // TODO: Check if user has completed habits today
                    val hasCompletedHabitsToday = false // Placeholder

                    if (!hasCompletedHabitsToday) {
                        notificationManager.sendStreakProtectionAlert(streakDays, hoursLeft)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun handleWorkoutReminder(
        context: Context,
        notificationManager: FitIntentNotificationManager
    ) {
        val workoutMessages = listOf(
            "Time to get your sweat on! 💦",
            "Your body is ready for action! 🏃‍♂️",
            "Let's build that strength! 💪",
            "Workout time - you've got this! 🔥",
            "Move your body, feel amazing! ⚡"
        )

        val message = workoutMessages.random()
        notificationManager.sendWorkoutReminder(
            title = "💪 Workout Time!",
            message = message
        )
    }

    private fun createMockHabit(habitId: Long, habitName: String): com.campus.fitintent.models.Habit {
        // TODO: Replace with actual Habit model
        return com.campus.fitintent.models.Habit(
            id = habitId,
            userId = 1L,
            name = habitName,
            description = "Complete this habit to build consistency",
            category = com.campus.fitintent.models.HabitCategory.OTHER,
            frequency = com.campus.fitintent.models.HabitFrequency.DAILY,
            reminderTime = "09:00",
            isActive = true
        )
    }
}

/**
 * Boot receiver to reschedule reminders after device restart
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val scheduler = ReminderScheduler(context)
            scheduler.rescheduleReminders()
        }
    }
}
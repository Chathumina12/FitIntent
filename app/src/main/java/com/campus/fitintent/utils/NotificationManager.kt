package com.campus.fitintent.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.campus.fitintent.R
import com.campus.fitintent.activities.MainActivity
import com.campus.fitintent.models.Badge
import com.campus.fitintent.models.Habit

/**
 * Manages all notifications for FitIntent app
 * Handles daily reminders, habit notifications, achievement notifications, and streak alerts
 */
class FitIntentNotificationManager(private val context: Context) {

    companion object {
        // Notification Channels
        private const val CHANNEL_DAILY_REMINDER = "daily_reminder"
        private const val CHANNEL_HABIT_REMINDER = "habit_reminder"
        private const val CHANNEL_ACHIEVEMENT = "achievement"
        private const val CHANNEL_STREAK_ALERT = "streak_alert"
        private const val CHANNEL_WORKOUT_REMINDER = "workout_reminder"

        // Notification IDs
        private const val NOTIFICATION_ID_DAILY_REMINDER = 1001
        private const val NOTIFICATION_ID_HABIT_BASE = 2000
        private const val NOTIFICATION_ID_ACHIEVEMENT_BASE = 3000
        private const val NOTIFICATION_ID_STREAK_ALERT = 4001
        private const val NOTIFICATION_ID_WORKOUT_REMINDER = 5001

        // Intent Actions
        const val ACTION_COMPLETE_HABIT = "com.campus.fitintent.COMPLETE_HABIT"
        const val ACTION_START_WORKOUT = "com.campus.fitintent.START_WORKOUT"
        const val ACTION_VIEW_ACHIEVEMENTS = "com.campus.fitintent.VIEW_ACHIEVEMENTS"

        // Extra Keys
        const val EXTRA_HABIT_ID = "habit_id"
        const val EXTRA_BADGE_ID = "badge_id"
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannels()
    }

    /**
     * Create notification channels for Android 8.0+
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_DAILY_REMINDER,
                    "Daily Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Daily motivation and progress reminders"
                    enableLights(true)
                    lightColor = Color.GREEN
                    enableVibration(true)
                },

                NotificationChannel(
                    CHANNEL_HABIT_REMINDER,
                    "Habit Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders for specific habits"
                    enableLights(true)
                    lightColor = Color.BLUE
                    enableVibration(true)
                },

                NotificationChannel(
                    CHANNEL_ACHIEVEMENT,
                    "Achievements",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Badge unlocks and milestone celebrations"
                    enableLights(true)
                    lightColor = Color.YELLOW
                    enableVibration(true)
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                },

                NotificationChannel(
                    CHANNEL_STREAK_ALERT,
                    "Streak Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Streak milestone and protection alerts"
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                },

                NotificationChannel(
                    CHANNEL_WORKOUT_REMINDER,
                    "Workout Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Scheduled workout reminders"
                    enableLights(true)
                    lightColor = Color.CYAN
                    enableVibration(true)
                }
            )

            val systemNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach { channel ->
                systemNotificationManager.createNotificationChannel(channel)
            }
        }
    }

    /**
     * Send daily motivation reminder
     */
    fun sendDailyReminder(
        title: String = "Time for FitIntent! 💪",
        message: String = "Check your progress and complete today's habits",
        habitCount: Int = 0
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationText = if (habitCount > 0) {
            "$message\n$habitCount habits waiting for you!"
        } else {
            message
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_DAILY_REMINDER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(notificationText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationText))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(context.getColor(R.color.green_primary))
            .addAction(
                R.drawable.ic_play,
                "Open App",
                pendingIntent
            )
            .build()

        notificationManager.notify(NOTIFICATION_ID_DAILY_REMINDER, notification)
    }

    /**
     * Send habit-specific reminder
     */
    fun sendHabitReminder(habit: Habit, streakDays: Int = 0) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "habits")
            putExtra(EXTRA_HABIT_ID, habit.id)
        }

        val pendingIntent = PendingIntent.getActivity(
            context, habit.id.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Quick complete action
        val completeIntent = Intent(context, MainActivity::class.java).apply {
            action = ACTION_COMPLETE_HABIT
            putExtra(EXTRA_HABIT_ID, habit.id)
        }

        val completePendingIntent = PendingIntent.getActivity(
            context, (NOTIFICATION_ID_HABIT_BASE + habit.id).toInt(), completeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🎯 ${habit.name}"
        val message = if (streakDays > 0) {
            "Keep your ${streakDays}-day streak alive! ${habit.description}"
        } else {
            "Time to work on: ${habit.description}"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_HABIT_REMINDER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(context.getColor(R.color.blue_primary))
            .addAction(
                R.drawable.ic_check,
                "Mark Complete",
                completePendingIntent
            )
            .addAction(
                R.drawable.ic_open,
                "Open",
                pendingIntent
            )
            .build()

        notificationManager.notify(
            NOTIFICATION_ID_HABIT_BASE + habit.id.toInt(),
            notification
        )
    }

    /**
     * Send badge unlock achievement notification
     */
    fun sendBadgeUnlockNotification(badge: Badge, pointsEarned: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "rewards")
            putExtra(EXTRA_BADGE_ID, badge.id)
        }

        val pendingIntent = PendingIntent.getActivity(
            context, badge.id.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🏆 Badge Unlocked!"
        val message = "Congratulations! You earned \"${badge.name}\" (+${pointsEarned} points)"

        val notification = NotificationCompat.Builder(context, CHANNEL_ACHIEVEMENT)
            .setSmallIcon(R.drawable.ic_trophy)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "$message\n\n${badge.description}"
            ))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(R.color.yellow_primary))
            .addAction(
                R.drawable.ic_trophy,
                "View Achievements",
                pendingIntent
            )
            .build()

        notificationManager.notify(
            NOTIFICATION_ID_ACHIEVEMENT_BASE + badge.id.toInt(),
            notification
        )
    }

    /**
     * Send streak milestone notification
     */
    fun sendStreakMilestoneNotification(streakDays: Int, bonusPoints: Int = 0) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "rewards")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, streakDays, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🔥 ${streakDays}-Day Streak!"
        val message = when (streakDays) {
            3 -> "Great start! 3 days in a row - you're building momentum!"
            7 -> "One week strong! Your consistency is paying off!"
            21 -> "Habit formed! 21 days of dedication - you're amazing!"
            30 -> "One month milestone! You're a true habit master!"
            90 -> "90 days! You've transformed your lifestyle!"
            365 -> "ONE YEAR STREAK! You're a FitIntent legend!"
            else -> "Amazing consistency! $streakDays days in a row!"
        }

        val fullMessage = if (bonusPoints > 0) {
            "$message\n\nBonus: +$bonusPoints points earned!"
        } else {
            message
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_STREAK_ALERT)
            .setSmallIcon(R.drawable.ic_fire)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(fullMessage))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(R.color.red_primary))
            .addAction(
                R.drawable.ic_share,
                "Share Achievement",
                createShareIntent(title, message)
            )
            .build()

        notificationManager.notify(NOTIFICATION_ID_STREAK_ALERT, notification)
    }

    /**
     * Send streak protection alert (when user is about to lose streak)
     */
    fun sendStreakProtectionAlert(streakDays: Int, hoursLeft: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "habits")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 999, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🚨 Protect Your Streak!"
        val message = "You have $hoursLeft hours left to save your ${streakDays}-day streak!"

        val notification = NotificationCompat.Builder(context, CHANNEL_STREAK_ALERT)
            .setSmallIcon(R.drawable.ic_warning)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "$message\n\nComplete your habits now to keep the momentum going!"
            ))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(R.color.red_primary))
            .addAction(
                R.drawable.ic_play,
                "Complete Habits",
                pendingIntent
            )
            .build()

        notificationManager.notify(NOTIFICATION_ID_STREAK_ALERT + 1, notification)
    }

    /**
     * Send workout reminder notification
     */
    fun sendWorkoutReminder(
        title: String = "💪 Workout Time!",
        message: String = "Time for your scheduled workout session"
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "workouts")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val workoutIntent = Intent(context, MainActivity::class.java).apply {
            action = ACTION_START_WORKOUT
            putExtra("fragment", "workouts")
        }

        val workoutPendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID_WORKOUT_REMINDER, workoutIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_WORKOUT_REMINDER)
            .setSmallIcon(R.drawable.ic_dumbbell)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(context.getColor(R.color.orange_primary))
            .addAction(
                R.drawable.ic_play,
                "Start Workout",
                workoutPendingIntent
            )
            .addAction(
                R.drawable.ic_schedule,
                "Reschedule",
                pendingIntent
            )
            .build()

        notificationManager.notify(NOTIFICATION_ID_WORKOUT_REMINDER, notification)
    }

    /**
     * Send level up notification
     */
    fun sendLevelUpNotification(newLevel: Int, levelTitle: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "rewards")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, newLevel, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🎉 Level Up!"
        val message = "Congratulations! You're now Level $newLevel - $levelTitle"

        val notification = NotificationCompat.Builder(context, CHANNEL_ACHIEVEMENT)
            .setSmallIcon(R.drawable.ic_level_up)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "$message\n\nYour dedication is paying off - keep up the amazing work!"
            ))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(R.color.purple_primary))
            .build()

        notificationManager.notify(NOTIFICATION_ID_ACHIEVEMENT_BASE + newLevel, notification)
    }

    /**
     * Send perfect week celebration
     */
    fun sendPerfectWeekNotification(weekNumber: Int, bonusPoints: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment", "rewards")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, weekNumber, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "🌟 Perfect Week!"
        val message = "Amazing! You completed all your goals this week (+$bonusPoints bonus points)"

        val notification = NotificationCompat.Builder(context, CHANNEL_ACHIEVEMENT)
            .setSmallIcon(R.drawable.ic_star)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "$message\n\nYour consistency and dedication are truly inspiring!"
            ))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(R.color.green_primary))
            .build()

        notificationManager.notify(NOTIFICATION_ID_ACHIEVEMENT_BASE + 999, notification)
    }

    /**
     * Create share intent for achievements
     */
    private fun createShareIntent(title: String, message: String): PendingIntent {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title\n\n$message\n\n📱 Shared from FitIntent")
            putExtra(Intent.EXTRA_SUBJECT, title)
        }

        val chooserIntent = Intent.createChooser(shareIntent, "Share Achievement")

        return PendingIntent.getActivity(
            context, 888, chooserIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Cancel specific notification
     */
    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    /**
     * Cancel habit reminder
     */
    fun cancelHabitReminder(habitId: Long) {
        notificationManager.cancel(NOTIFICATION_ID_HABIT_BASE + habitId.toInt())
    }

    /**
     * Cancel all notifications
     */
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }

    /**
     * Check if notifications are enabled
     */
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}
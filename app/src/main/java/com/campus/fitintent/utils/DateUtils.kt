package com.campus.fitintent.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class for date operations
 */
object DateUtils {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val displayTimeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    /**
     * Get start of day (00:00:00) for a given date
     */
    fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * Get end of day (23:59:59) for a given date
     */
    fun getEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    /**
     * Check if two dates are on the same day
     */
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * Check if date is today
     */
    fun isToday(date: Date): Boolean {
        return isSameDay(date, Date())
    }

    /**
     * Check if date is yesterday
     */
    fun isYesterday(date: Date): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return isSameDay(date, yesterday.time)
    }

    /**
     * Get date N days ago
     */
    fun getDateDaysAgo(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return getStartOfDay(calendar.time)
    }

    /**
     * Get date N days from now
     */
    fun getDateDaysFromNow(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return getStartOfDay(calendar.time)
    }

    /**
     * Get start of week (Monday)
     */
    fun getStartOfWeek(date: Date = Date()): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        return getStartOfDay(calendar.time)
    }

    /**
     * Get end of week (Sunday)
     */
    fun getEndOfWeek(date: Date = Date()): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        return getEndOfDay(calendar.time)
    }

    /**
     * Get start of month
     */
    fun getStartOfMonth(date: Date = Date()): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return getStartOfDay(calendar.time)
    }

    /**
     * Get end of month
     */
    fun getEndOfMonth(date: Date = Date()): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return getEndOfDay(calendar.time)
    }

    /**
     * Format date for display
     */
    fun formatDate(date: Date): String {
        return displayDateFormat.format(date)
    }

    /**
     * Format time for display
     */
    fun formatTime(date: Date): String {
        return displayTimeFormat.format(date)
    }

    /**
     * Format date and time for display
     */
    fun formatDateTime(date: Date): String {
        return "${ formatDate(date) } at ${ formatTime(date) }"
    }

    /**
     * Format date as relative time (e.g., "2 hours ago", "Yesterday")
     */
    fun formatRelativeTime(date: Date): String {
        val now = Date()
        val diff = now.time - date.time

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            days == 1L -> "Yesterday"
            days < 7 -> "$days days ago"
            days < 30 -> "${days / 7} week${if (days / 7 > 1) "s" else ""} ago"
            days < 365 -> "${days / 30} month${if (days / 30 > 1) "s" else ""} ago"
            else -> formatDate(date)
        }
    }

    /**
     * Parse time string (HH:mm) to calendar components
     */
    fun parseTime(timeString: String): Pair<Int, Int>? {
        return try {
            val parts = timeString.split(":")
            if (parts.size == 2) {
                val hour = parts[0].toInt()
                val minute = parts[1].toInt()
                Pair(hour, minute)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convert minutes to formatted duration (e.g., "1h 30m")
     */
    fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60

        return when {
            hours > 0 && mins > 0 -> "${hours}h ${mins}m"
            hours > 0 -> "${hours}h"
            else -> "${mins}m"
        }
    }

    /**
     * Get age from birth date
     */
    fun getAge(birthDate: Date): Int {
        val birth = Calendar.getInstance()
        birth.time = birthDate

        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    /**
     * Get days between two dates
     */
    fun getDaysBetween(startDate: Date, endDate: Date): Long {
        val diff = endDate.time - startDate.time
        return diff / (1000 * 60 * 60 * 24)
    }
}
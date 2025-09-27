package com.campus.fitintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.campus.fitintent.database.dao.*
import com.campus.fitintent.models.*

/**
 * Room database for FitIntent application
 * Manages all local data persistence
 */
@Database(
    entities = [
        User::class,
        Habit::class,
        HabitLog::class,
        Workout::class,
        Exercise::class,
        WorkoutSession::class,
        DailyGoal::class,
        Streak::class,
        Badge::class,
        UserBadge::class,
        NutritionTip::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FitIntentDatabase : RoomDatabase() {
    // User DAOs
    abstract fun userDao(): UserDao

    // Habit DAOs
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao

    // Workout DAOs
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao

    // Progress DAOs (interfaces defined in ProgressDao.kt)
    abstract fun dailyGoalDao(): DailyGoalDao
    abstract fun streakDao(): StreakDao
    abstract fun userBadgeDao(): UserBadgeDao

    // Nutrition DAOs
    abstract fun nutritionTipDao(): NutritionTipDao
}
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
        NutritionTip::class,
        QuizAnswer::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class FitIntentDatabase : RoomDatabase() {
    // User DAOs
    abstract fun userDao(): UserDao
    abstract fun quizAnswerDao(): QuizAnswerDao

    // Habit DAOs
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao

    // Workout DAOs
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao

    // Progress DAOs
    abstract fun dailyGoalDao(): DailyGoalDao
    abstract fun streakDao(): StreakDao
    abstract fun badgeDao(): BadgeDao

    // Nutrition DAOs
    abstract fun nutritionTipDao(): NutritionTipDao
}
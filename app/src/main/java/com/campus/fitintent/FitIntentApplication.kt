package com.campus.fitintent

import android.app.Application
import androidx.room.Room
import com.campus.fitintent.database.FitIntentDatabase
import com.campus.fitintent.repository.*
import com.campus.fitintent.utils.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Main Application class for FitIntent
 * Initializes database, repositories, and application-wide components
 */
class FitIntentApplication : Application() {

    // Application-wide coroutine scope for long-running operations
    val applicationScope = CoroutineScope(SupervisorJob())

    // Database instance
    lateinit var database: FitIntentDatabase
        private set

    // Repositories
    lateinit var userRepository: UserRepository
        private set
    lateinit var habitRepository: HabitRepository
        private set
    lateinit var workoutRepository: WorkoutRepository
        private set
    lateinit var nutritionRepository: NutritionRepository
        private set
    lateinit var progressRepository: ProgressRepository
        private set

    // Preferences Manager
    lateinit var preferencesManager: PreferencesManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeComponents()
    }

    private fun initializeComponents() {
        // Initialize database
        database = Room.databaseBuilder(
            applicationContext,
            FitIntentDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Only for development
            .build()

        // Initialize preferences
        preferencesManager = PreferencesManager(this)

        // Initialize repositories
        userRepository = UserRepository(database.userDao(), preferencesManager)
        habitRepository = HabitRepository(database.habitDao(), database.habitLogDao())
        workoutRepository = WorkoutRepository(
            database.workoutDao(),
            database.workoutSessionDao(),
            database.exerciseDao()
        )
        nutritionRepository = NutritionRepository(database.nutritionTipDao())
        progressRepository = ProgressRepository(
            database.userBadgeDao(),
            database.streakDao(),
            database.dailyGoalDao()
        )
    }

    companion object {
        const val DATABASE_NAME = "fitintent_database"

        @Volatile
        private var instance: FitIntentApplication? = null

        fun getInstance(): FitIntentApplication {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }
}
package com.campus.fitintent.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.FitIntentApplication

/**
 * Factory for creating ViewModels with dependencies
 */
class ViewModelFactory(
    private val application: FitIntentApplication
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(application.userRepository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(
                    application.userRepository,
                    application.habitRepository,
                    application.workoutRepository,
                    application.progressRepository,
                    application.nutritionRepository
                ) as T
            }
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                HabitViewModel(
                    application.habitRepository,
                    application.userRepository,
                    application.progressRepository
                ) as T
            }
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> {
                WorkoutViewModel(
                    application.workoutRepository,
                    application.userRepository,
                    application.progressRepository
                ) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(
                    application.userRepository,
                    application.progressRepository
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: FitIntentApplication): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(application)
                INSTANCE = instance
                instance
            }
        }
    }
}
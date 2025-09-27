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
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(application.userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(application.userRepository) as T
            }
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(application.userRepository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(application.userRepository) as T
            }
            modelClass.isAssignableFrom(NutritionViewModel::class.java) -> {
                NutritionViewModel(application.nutritionRepository) as T
            }
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                HabitViewModel(
                    application.habitRepository,
                    application.userRepository,
                    application.progressRepository
                ) as T
            }
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> {
                WorkoutViewModel(application.workoutRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(application) as T
            }
            modelClass.isAssignableFrom(RewardsViewModel::class.java) -> {
                RewardsViewModel(
                    application.progressRepository,
                    application.userRepository
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
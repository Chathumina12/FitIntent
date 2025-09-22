package com.campus.fitintent.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.repository.*
import com.campus.fitintent.viewmodels.*

class ViewModelFactory(
    private val userRepository: UserRepository? = null,
    private val habitRepository: HabitRepository? = null,
    private val workoutRepository: WorkoutRepository? = null,
    private val nutritionRepository: NutritionRepository? = null,
    private val progressRepository: ProgressRepository? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(userRepository!!) as T
            }
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(userRepository!!) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(userRepository!!) as T
            }
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                HabitViewModel(habitRepository!!) as T
            }
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> {
                WorkoutViewModel(workoutRepository!!) as T
            }
            modelClass.isAssignableFrom(NutritionViewModel::class.java) -> {
                NutritionViewModel(nutritionRepository!!) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository!!, progressRepository!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
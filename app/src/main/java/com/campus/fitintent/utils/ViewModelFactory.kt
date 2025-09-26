package com.campus.fitintent.utils

<<<<<<< HEAD
=======
import android.app.Application
>>>>>>> 818ab1f (Updated)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.repository.*
import com.campus.fitintent.viewmodels.*

class ViewModelFactory(
<<<<<<< HEAD
=======
    private val application: Application? = null,
>>>>>>> 818ab1f (Updated)
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
<<<<<<< HEAD
                HabitViewModel(habitRepository!!) as T
=======
                HabitViewModel(habitRepository!!, userRepository!!, progressRepository!!) as T
>>>>>>> 818ab1f (Updated)
            }
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> {
                WorkoutViewModel(workoutRepository!!) as T
            }
            modelClass.isAssignableFrom(NutritionViewModel::class.java) -> {
                NutritionViewModel(nutritionRepository!!) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
<<<<<<< HEAD
                ProfileViewModel(userRepository!!, progressRepository!!) as T
=======
                ProfileViewModel(application!!) as T
>>>>>>> 818ab1f (Updated)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
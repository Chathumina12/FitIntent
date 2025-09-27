package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.User
import com.campus.fitintent.repository.UserRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentStep = MutableLiveData(0)
    val currentStep: LiveData<Int> = _currentStep

    private val _onboardingComplete = MutableLiveData<Boolean>()
    val onboardingComplete: LiveData<Boolean> = _onboardingComplete

    // Quiz answers
    private var fitnessGoal: String? = null
    private var exerciseFrequency: String? = null
    private var workoutPreference: String? = null
    private var fitnessLevel: String? = null
    private var timeAvailability: String? = null

    fun nextStep() {
        val current = _currentStep.value ?: 0
        if (current < 4) { // 5 questions (0-4)
            _currentStep.value = current + 1
        }
    }

    fun previousStep() {
        val current = _currentStep.value ?: 0
        if (current > 0) {
            _currentStep.value = current - 1
        }
    }

    fun setFitnessGoal(goal: String) {
        fitnessGoal = goal
        nextStep()
    }

    fun setExerciseFrequency(frequency: String) {
        exerciseFrequency = frequency
        nextStep()
    }

    fun setWorkoutPreference(preference: String) {
        workoutPreference = preference
        nextStep()
    }

    fun setFitnessLevel(level: String) {
        fitnessLevel = level
        nextStep()
    }

    fun setTimeAvailability(time: String) {
        timeAvailability = time
        finishOnboarding()
    }

    fun completeOnboarding() {
        finishOnboarding()
    }

    private fun finishOnboarding() {
        viewModelScope.launch {
            try {
                when (val result = userRepository.getCurrentUser()) {
                    is com.campus.fitintent.utils.Result.Success -> {
                        val user = result.data
                        // Update user profile with quiz answers
                        when (val updateResult = userRepository.updateProfile(
                            age = user.age,
                            gender = user.gender?.name,
                            height = user.height,
                            weight = user.weight,
                            activityLevel = mapActivityLevel(exerciseFrequency)?.name,
                            primaryGoal = mapFitnessGoal(fitnessGoal)?.name,
                            targetWeight = user.targetWeight,
                            weeklyWorkoutGoal = user.weeklyWorkoutGoal
                        )) {
                            is com.campus.fitintent.utils.Result.Success -> {
                                // Complete onboarding
                                when (userRepository.completeOnboarding()) {
                                    is com.campus.fitintent.utils.Result.Success -> {
                                        // Generate personalized plan based on answers
                                        generatePersonalizedPlan()
                                        _onboardingComplete.value = true
                                    }
                                    is com.campus.fitintent.utils.Result.Error -> {
                                        _onboardingComplete.value = false
                                    }
                                    is com.campus.fitintent.utils.Result.Loading -> {
                                        // This shouldn't happen
                                        _onboardingComplete.value = false
                                    }
                                }
                            }
                            is com.campus.fitintent.utils.Result.Error -> {
                                _onboardingComplete.value = false
                            }
                            is com.campus.fitintent.utils.Result.Loading -> {
                                // This shouldn't happen
                                _onboardingComplete.value = false
                            }
                        }
                    }
                    is com.campus.fitintent.utils.Result.Error -> {
                        _onboardingComplete.value = false
                    }
                    is com.campus.fitintent.utils.Result.Loading -> {
                        // This shouldn't happen
                        _onboardingComplete.value = false
                    }
                }
            } catch (e: Exception) {
                // Handle error
                _onboardingComplete.value = false
            }
        }
    }

    private fun generatePersonalizedPlan() {
        // TODO: Create personalized workout and habit recommendations based on quiz answers
        // This would involve creating default habits, selecting appropriate workouts,
        // and setting initial goals based on the user's responses
    }

    private fun mapFitnessGoal(goal: String?): com.campus.fitintent.models.FitnessGoal? {
        return when (goal) {
            "lose_weight" -> com.campus.fitintent.models.FitnessGoal.LOSE_WEIGHT
            "gain_strength" -> com.campus.fitintent.models.FitnessGoal.GAIN_MUSCLE
            "stay_active" -> com.campus.fitintent.models.FitnessGoal.MAINTAIN_FITNESS
            else -> null
        }
    }

    private fun mapActivityLevel(frequency: String?): com.campus.fitintent.models.ActivityLevel? {
        return when (frequency) {
            "rarely" -> com.campus.fitintent.models.ActivityLevel.SEDENTARY
            "1_2_times_week" -> com.campus.fitintent.models.ActivityLevel.LIGHTLY_ACTIVE
            "3_5_times_week" -> com.campus.fitintent.models.ActivityLevel.MODERATELY_ACTIVE
            "daily" -> com.campus.fitintent.models.ActivityLevel.VERY_ACTIVE
            else -> null
        }
    }
}
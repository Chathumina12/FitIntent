package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * User entity representing a registered user in the FitIntent app
 */
@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val email: String,
    val username: String,
    val passwordHash: String, // BCrypt hashed password
    val fullName: String,

    // Profile information
    val age: Int? = null,
    val gender: Gender? = null,
    val height: Float? = null, // in cm
    val weight: Float? = null, // in kg
    val activityLevel: ActivityLevel? = null,

    // Fitness goals from onboarding
    val primaryGoal: FitnessGoal? = null,
    val targetWeight: Float? = null,
    val weeklyWorkoutGoal: Int = 3,

    // Account metadata
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isOnboardingComplete: Boolean = false,
    val isActive: Boolean = true,
    val profileImagePath: String? = null
) {
    // Calculate BMI
    fun calculateBMI(): Float? {
        return if (height != null && weight != null && height > 0) {
            val heightInMeters = height / 100
            weight / (heightInMeters * heightInMeters)
        } else null
    }

    // Get BMI category
    fun getBMICategory(): BMICategory? {
        val bmi = calculateBMI() ?: return null
        return when {
            bmi < 18.5 -> BMICategory.UNDERWEIGHT
            bmi < 25 -> BMICategory.NORMAL
            bmi < 30 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}

enum class ActivityLevel {
    SEDENTARY,      // Little or no exercise
    LIGHTLY_ACTIVE, // Light exercise 1-3 days/week
    MODERATELY_ACTIVE, // Moderate exercise 3-5 days/week
    VERY_ACTIVE,    // Hard exercise 6-7 days/week
    EXTRA_ACTIVE    // Very hard exercise & physical job
}

enum class FitnessGoal {
    LOSE_WEIGHT,
    GAIN_MUSCLE,
    IMPROVE_ENDURANCE,
    MAINTAIN_FITNESS,
    INCREASE_FLEXIBILITY,
    GENERAL_HEALTH
}

enum class BMICategory {
    UNDERWEIGHT,
    NORMAL,
    OVERWEIGHT,
    OBESE
}
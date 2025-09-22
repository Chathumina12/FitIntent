package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Nutrition tips for user guidance
 */
@Entity(tableName = "nutrition_tips")
data class NutritionTip(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val content: String,
    val category: NutritionCategory,
    val targetGoal: FitnessGoal? = null, // Null means applicable to all
    val difficultyLevel: DifficultyLevel = DifficultyLevel.BEGINNER,
    val imageUrl: String? = null,
    val sourceUrl: String? = null,
    val calories: Int? = null,
    val protein: Float? = null, // in grams
    val carbs: Float? = null, // in grams
    val fats: Float? = null, // in grams
    val fiber: Float? = null, // in grams
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false,
    val isGlutenFree: Boolean = false,
    val preparationTime: Int? = null, // in minutes
    val servingSize: String? = null,
    val ingredients: String? = null, // JSON or comma-separated
    val instructions: String? = null,
    val tags: String? = null, // Comma-separated tags
    val createdAt: Date = Date()
)

enum class NutritionCategory {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    PRE_WORKOUT,
    POST_WORKOUT,
    HYDRATION,
    SUPPLEMENT,
    MEAL_PREP,
    HEALTHY_TIPS,
    RECIPES
}

/**
 * Quiz answers from onboarding
 */
@Entity(
    tableName = "quiz_answers",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class QuizAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val questionId: String, // Unique identifier for the question
    val questionText: String,
    val answerValue: String,
    val answerType: AnswerType,
    val answeredAt: Date = Date()
)

enum class AnswerType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    NUMERIC,
    TEXT,
    BOOLEAN
}
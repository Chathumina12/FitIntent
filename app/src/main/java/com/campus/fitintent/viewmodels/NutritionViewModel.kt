package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.NutritionRepository
import kotlinx.coroutines.launch

data class DailySummary(
    val calories: Int,
    val calorieGoal: Int,
    val protein: Int,
    val proteinGoal: Int,
    val waterGlasses: Int,
    val waterGoal: Int = 8
)

data class MealSuggestion(
    val id: Long,
    val name: String,
    val calories: Int,
    val protein: Int,
    val imageResource: String,
    val mealType: String // breakfast, lunch, dinner, snack
)

class NutritionViewModel(
    private val nutritionRepository: NutritionRepository
) : ViewModel() {

    private val _dailySummary = MutableLiveData<DailySummary>()
    val dailySummary: LiveData<DailySummary> = _dailySummary

    private val _nutritionTips = MutableLiveData<List<NutritionTip>>()
    val nutritionTips: LiveData<List<NutritionTip>> = _nutritionTips

    private val _mealSuggestions = MutableLiveData<List<MealSuggestion>>()
    val mealSuggestions: LiveData<List<MealSuggestion>> = _mealSuggestions

    init {
        loadNutritionData()
    }

    private fun loadNutritionData() {
        viewModelScope.launch {
            try {
                // Load daily summary
                loadDailySummary()

                // Load nutrition tips
                loadNutritionTips()

                // Load meal suggestions
                loadMealSuggestions()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun loadDailySummary() {
        // TODO: Load daily nutrition summary from database
        // For now, using mock data
        _dailySummary.value = DailySummary(
            calories = 1500,
            calorieGoal = 2000,
            protein = 65,
            proteinGoal = 80,
            waterGlasses = 5,
            waterGoal = 8
        )
    }

    private suspend fun loadNutritionTips() {
        // TODO: Load nutrition tips from database
        // For now, using mock data
        val mockTips = listOf(
            NutritionTip(
                id = 1,
                title = "Hydration is Key",
                description = "Drink at least 8 glasses of water daily for optimal performance",
                category = "Hydration",
                imageResource = "ic_water",
                relatedGoal = FitnessGoal.MAINTAIN_FITNESS,
                createdAt = System.currentTimeMillis()
            ),
            NutritionTip(
                id = 2,
                title = "Protein Power",
                description = "Include protein in every meal to build and maintain muscle",
                category = "Protein",
                imageResource = "ic_protein",
                relatedGoal = FitnessGoal.GAIN_MUSCLE,
                createdAt = System.currentTimeMillis()
            ),
            NutritionTip(
                id = 3,
                title = "Balanced Breakfast",
                description = "Start your day with a mix of complex carbs and protein",
                category = "Meals",
                imageResource = "ic_breakfast",
                relatedGoal = FitnessGoal.LOSE_WEIGHT,
                createdAt = System.currentTimeMillis()
            )
        )
        _nutritionTips.value = mockTips
    }

    private suspend fun loadMealSuggestions() {
        // TODO: Load meal suggestions from database
        // For now, using mock data
        val mockMeals = listOf(
            MealSuggestion(
                id = 1,
                name = "Greek Yogurt Parfait",
                calories = 250,
                protein = 15,
                imageResource = "meal_parfait",
                mealType = "breakfast"
            ),
            MealSuggestion(
                id = 2,
                name = "Grilled Chicken Salad",
                calories = 350,
                protein = 35,
                imageResource = "meal_salad",
                mealType = "lunch"
            ),
            MealSuggestion(
                id = 3,
                name = "Protein Smoothie",
                calories = 200,
                protein = 25,
                imageResource = "meal_smoothie",
                mealType = "snack"
            ),
            MealSuggestion(
                id = 4,
                name = "Quinoa Bowl",
                calories = 400,
                protein = 20,
                imageResource = "meal_quinoa",
                mealType = "dinner"
            )
        )
        _mealSuggestions.value = mockMeals
    }

    fun addWaterGlass() {
        val current = _dailySummary.value ?: return
        if (current.waterGlasses < current.waterGoal) {
            _dailySummary.value = current.copy(waterGlasses = current.waterGlasses + 1)

            viewModelScope.launch {
                try {
                    // TODO: Save water intake to database
                    // For now, just updating local state
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }
    }

    fun trackMeal(calories: Int, protein: Int, mealType: String) {
        viewModelScope.launch {
            try {
                // TODO: Save meal to database
                // Update daily summary
                val current = _dailySummary.value ?: return@launch
                _dailySummary.value = current.copy(
                    calories = current.calories + calories,
                    protein = current.protein + protein
                )
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun refreshNutritionData() {
        loadNutritionData()
    }
}
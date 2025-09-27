package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.NutritionRepository
import java.util.Date
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

    // Additional properties expected by NutritionFragment
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allTips = MutableLiveData<List<NutritionTip>>()
    val allTips: LiveData<List<NutritionTip>> = _allTips

    private val _todaysTip = MutableLiveData<NutritionTip?>()
    val todaysTip: LiveData<NutritionTip?> = _todaysTip

    private val _userFavorites = MutableLiveData<Set<Long>>()
    val userFavorites: LiveData<Set<Long>> = _userFavorites

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadNutritionData()
    }

    private fun loadNutritionData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Load daily summary
                loadDailySummary()

                // Load nutrition tips
                loadNutritionTips()

                // Load all tips for filtering
                loadAllTips()

                // Load today's tip
                loadTodaysTip()

                // Load user favorites
                loadUserFavorites()

                // Load meal suggestions
                loadMealSuggestions()
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred while loading nutrition data"
            } finally {
                _isLoading.value = false
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
                category = NutritionCategory.HYDRATION,
                icon = "ic_water",
                tags = "hydration,water,performance",
                createdAt = java.util.Date(System.currentTimeMillis())
            ),
            NutritionTip(
                id = 2,
                title = "Protein Power",
                description = "Include protein in every meal to build and maintain muscle",
                category = NutritionCategory.PROTEIN,
                icon = "ic_protein",
                tags = "protein,muscle,nutrition",
                createdAt = java.util.Date(System.currentTimeMillis())
            ),
            NutritionTip(
                id = 3,
                title = "Balanced Breakfast",
                description = "Start your day with a mix of complex carbs and protein",
                category = NutritionCategory.MEAL_TIMING,
                icon = "ic_breakfast",
                tags = "breakfast,carbs,protein",
                createdAt = java.util.Date(System.currentTimeMillis())
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

    // Additional methods expected by NutritionFragment
    fun loadAllTips() {
        viewModelScope.launch {
            try {
                // TODO: Load all tips from repository
                // For now, using mock data from loadNutritionTips()
                val mockTips = listOf(
                    NutritionTip(
                        id = 1,
                        title = "Hydration is Key",
                        description = "Drink at least 8 glasses of water daily for optimal performance",
                        category = NutritionCategory.HYDRATION,
                        icon = "ic_water",
                        tags = "hydration,water,performance",
                        createdAt = java.util.Date(System.currentTimeMillis())
                    ),
                    NutritionTip(
                        id = 2,
                        title = "Protein Power",
                        description = "Include protein in every meal to build and maintain muscle",
                        category = NutritionCategory.PROTEIN,
                        icon = "ic_protein",
                        tags = "protein,muscle,nutrition",
                        createdAt = java.util.Date(System.currentTimeMillis())
                    ),
                    NutritionTip(
                        id = 3,
                        title = "Balanced Breakfast",
                        description = "Start your day with a mix of complex carbs and protein",
                        category = NutritionCategory.MEAL_TIMING,
                        icon = "ic_breakfast",
                        tags = "breakfast,carbs,protein",
                        createdAt = java.util.Date(System.currentTimeMillis())
                    )
                )
                _allTips.value = mockTips
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load nutrition tips"
            }
        }
    }

    fun loadTodaysTip() {
        viewModelScope.launch {
            try {
                // TODO: Get today's tip from repository based on rotation logic
                // For now, selecting first tip from mock data
                val mockTip = NutritionTip(
                    id = 1,
                    title = "Hydration is Key",
                    description = "Drink at least 8 glasses of water daily for optimal performance",
                    category = NutritionCategory.HYDRATION,
                    icon = "ic_water",
                    tags = "hydration,water,performance",
                    createdAt = java.util.Date(System.currentTimeMillis())
                )
                _todaysTip.value = mockTip
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load today's tip"
            }
        }
    }

    fun loadUserFavorites() {
        viewModelScope.launch {
            try {
                // TODO: Load user favorites from repository
                // For now, using empty set
                _userFavorites.value = emptySet()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load user favorites"
            }
        }
    }

    fun toggleFavorite(tipId: Long) {
        viewModelScope.launch {
            try {
                val currentFavorites = _userFavorites.value ?: emptySet()
                val newFavorites = if (currentFavorites.contains(tipId)) {
                    currentFavorites - tipId
                } else {
                    currentFavorites + tipId
                }
                _userFavorites.value = newFavorites

                // TODO: Update favorites in repository
                // nutritionRepository.updateFavorite(tipId, newFavorites.contains(tipId))
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update favorite"
            }
        }
    }
}
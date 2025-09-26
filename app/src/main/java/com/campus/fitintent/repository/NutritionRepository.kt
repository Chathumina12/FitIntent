package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.NutritionTipDao
import com.campus.fitintent.models.FitnessGoal
import com.campus.fitintent.models.NutritionCategory
import com.campus.fitintent.models.NutritionTip
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository for Nutrition-related operations
 */
class NutritionRepository(
    private val nutritionTipDao: NutritionTipDao
) {
    /**
     * Get all nutrition tips
     */
    fun getAllTips(): Flow<List<NutritionTip>> {
        return nutritionTipDao.getAllTips()
    }

    /**
     * Get tips by category
     */
    fun getTipsByCategory(category: NutritionCategory): Flow<List<NutritionTip>> {
        return nutritionTipDao.getTipsByCategory(category)
    }

    /**
     * Get tips by fitness goal
     */
<<<<<<< HEAD
    fun getTipsByGoal(goal: FitnessGoal): Flow<List<NutritionTip>> {
        return nutritionTipDao.getTipsByGoal(goal)
=======
    fun getTipsByGoal(category: NutritionCategory): Flow<List<NutritionTip>> {
        return nutritionTipDao.getTipsByCategory(category)
>>>>>>> 818ab1f (Updated)
    }

    /**
     * Search nutrition tips
     */
    fun searchTips(query: String): Flow<List<NutritionTip>> {
        return nutritionTipDao.searchTips(query)
    }

    /**
     * Get vegetarian tips
     */
    fun getVegetarianTips(): Flow<List<NutritionTip>> {
        return nutritionTipDao.getVegetarianTips()
    }

    /**
     * Get vegan tips
     */
    fun getVeganTips(): Flow<List<NutritionTip>> {
        return nutritionTipDao.getVeganTips()
    }

    /**
     * Get daily nutrition tip
     */
    suspend fun getDailyTip(
        userGoal: FitnessGoal? = null,
        mealTime: NutritionCategory? = null
    ): Result<NutritionTip> = withContext(Dispatchers.IO) {
        try {
            val categories = when (mealTime) {
                NutritionCategory.BREAKFAST -> listOf(NutritionCategory.BREAKFAST, NutritionCategory.HEALTHY_TIPS)
                NutritionCategory.LUNCH -> listOf(NutritionCategory.LUNCH, NutritionCategory.HEALTHY_TIPS)
                NutritionCategory.DINNER -> listOf(NutritionCategory.DINNER, NutritionCategory.HEALTHY_TIPS)
                NutritionCategory.SNACK -> listOf(NutritionCategory.SNACK, NutritionCategory.HEALTHY_TIPS)
                else -> NutritionCategory.values().toList()
            }

<<<<<<< HEAD
            val tip = nutritionTipDao.getDailyTip(categories, userGoal)
=======
            val tip = nutritionTipDao.getDailyTip(categories)
>>>>>>> 818ab1f (Updated)
                ?: return@withContext Result.Error("No nutrition tips available")

            Result.Success(tip)
        } catch (e: Exception) {
            Result.Error("Failed to get daily tip: ${e.message}")
        }
    }

    /**
     * Initialize default nutrition tips
     */
    suspend fun initializeDefaultTips() = withContext(Dispatchers.IO) {
        try {
            // Check if tips already exist
            if (nutritionTipDao.getTipCount() > 0) {
                return@withContext
            }

            val defaultTips = listOf(
                // Breakfast tips
                NutritionTip(
                    title = "Start Your Day with Protein",
<<<<<<< HEAD
                    content = "Include protein-rich foods like eggs, Greek yogurt, or protein smoothies in your breakfast to keep you full and energized throughout the morning.",
                    category = NutritionCategory.BREAKFAST,
                    targetGoal = FitnessGoal.GAIN_MUSCLE,
                    protein = 25f,
                    isVegetarian = true
                ),
                NutritionTip(
                    title = "Overnight Oats for Busy Mornings",
                    content = "Prepare overnight oats with chia seeds, berries, and almond milk for a nutritious breakfast that's ready when you wake up.",
                    category = NutritionCategory.BREAKFAST,
                    targetGoal = FitnessGoal.GENERAL_HEALTH,
                    calories = 350,
                    fiber = 8f,
                    isVegan = true
=======
                    description = "Include protein-rich foods like eggs, Greek yogurt, or protein smoothies in your breakfast to keep you full and energized throughout the morning.",
                    category = NutritionCategory.BREAKFAST,
                    benefits = "Sustained energy, muscle maintenance, improved satiety",
                    tags = "protein,breakfast,energy,muscle"
                ),
                NutritionTip(
                    title = "Overnight Oats for Busy Mornings",
                    description = "Prepare overnight oats with chia seeds, berries, and almond milk for a nutritious breakfast that's ready when you wake up.",
                    category = NutritionCategory.BREAKFAST,
                    benefits = "Time-saving, fiber-rich, antioxidants from berries",
                    tags = "oats,fiber,meal prep,vegan"
>>>>>>> 818ab1f (Updated)
                ),

                // Lunch tips
                NutritionTip(
                    title = "Balanced Lunch Bowl",
<<<<<<< HEAD
                    content = "Create a balanced lunch with lean protein, complex carbs, healthy fats, and plenty of colorful vegetables for sustained energy.",
                    category = NutritionCategory.LUNCH,
                    targetGoal = FitnessGoal.MAINTAIN_FITNESS,
                    calories = 500
                ),
                NutritionTip(
                    title = "Mediterranean Power Lunch",
                    content = "Try a Mediterranean-inspired lunch with grilled chicken, quinoa, cucumber, tomatoes, and a drizzle of olive oil for heart health.",
                    category = NutritionCategory.LUNCH,
                    targetGoal = FitnessGoal.IMPROVE_ENDURANCE,
                    protein = 35f,
                    carbs = 45f
=======
                    description = "Create a balanced lunch with lean protein, complex carbs, healthy fats, and plenty of colorful vegetables for sustained energy.",
                    category = NutritionCategory.LUNCH,
                    benefits = "Balanced nutrition, sustained energy, nutrient variety",
                    tags = "balanced,vegetables,energy,nutrition"
                ),
                NutritionTip(
                    title = "Mediterranean Power Lunch",
                    description = "Try a Mediterranean-inspired lunch with grilled chicken, quinoa, cucumber, tomatoes, and a drizzle of olive oil for heart health.",
                    category = NutritionCategory.LUNCH,
                    benefits = "Heart health, lean protein, complex carbs",
                    tags = "mediterranean,heart health,chicken,quinoa"
>>>>>>> 818ab1f (Updated)
                ),

                // Pre-workout tips
                NutritionTip(
                    title = "Pre-Workout Fuel",
<<<<<<< HEAD
                    content = "Eat a banana with almond butter 30-60 minutes before your workout for quick energy and sustained performance.",
                    category = NutritionCategory.PRE_WORKOUT,
                    targetGoal = FitnessGoal.IMPROVE_ENDURANCE,
                    calories = 200,
                    carbs = 30f,
                    isVegan = true
=======
                    description = "Eat a banana with almond butter 30-60 minutes before your workout for quick energy and sustained performance.",
                    category = NutritionCategory.PRE_WORKOUT,
                    benefits = "Quick energy, sustained performance, natural sugars",
                    tags = "pre-workout,energy,banana,performance"
>>>>>>> 818ab1f (Updated)
                ),

                // Post-workout tips
                NutritionTip(
                    title = "Post-Workout Recovery",
<<<<<<< HEAD
                    content = "Within 30 minutes after exercise, consume a combination of protein and carbs to help muscle recovery and replenish energy stores.",
                    category = NutritionCategory.POST_WORKOUT,
                    targetGoal = FitnessGoal.GAIN_MUSCLE,
                    protein = 30f,
                    carbs = 40f
=======
                    description = "Within 30 minutes after exercise, consume a combination of protein and carbs to help muscle recovery and replenish energy stores.",
                    category = NutritionCategory.POST_WORKOUT,
                    benefits = "Muscle recovery, glycogen replenishment, faster adaptation",
                    tags = "post-workout,recovery,protein,carbs"
>>>>>>> 818ab1f (Updated)
                ),

                // Hydration tips
                NutritionTip(
                    title = "Stay Hydrated",
<<<<<<< HEAD
                    content = "Aim for at least 8 glasses of water daily, and increase intake during exercise. Add lemon or cucumber for flavor without calories.",
                    category = NutritionCategory.HYDRATION,
                    targetGoal = null,
                    calories = 0
=======
                    description = "Aim for at least 8 glasses of water daily, and increase intake during exercise. Add lemon or cucumber for flavor without calories.",
                    category = NutritionCategory.HYDRATION,
                    benefits = "Better performance, improved brain function, temperature regulation",
                    tags = "hydration,water,performance,health"
>>>>>>> 818ab1f (Updated)
                ),

                // Healthy snack tips
                NutritionTip(
                    title = "Smart Snacking",
<<<<<<< HEAD
                    content = "Choose nutrient-dense snacks like mixed nuts, apple slices with peanut butter, or Greek yogurt with berries.",
                    category = NutritionCategory.SNACK,
                    targetGoal = FitnessGoal.LOSE_WEIGHT,
                    calories = 150,
                    protein = 8f,
                    isVegetarian = true
=======
                    description = "Choose nutrient-dense snacks like mixed nuts, apple slices with peanut butter, or Greek yogurt with berries.",
                    category = NutritionCategory.SNACK,
                    benefits = "Sustained energy, healthy fats, protein, portion control",
                    tags = "snack,nuts,protein,portion control"
>>>>>>> 818ab1f (Updated)
                ),

                // Dinner tips
                NutritionTip(
                    title = "Light but Satisfying Dinner",
<<<<<<< HEAD
                    content = "For weight loss, try grilled fish with steamed vegetables and a small portion of sweet potato for a filling yet light dinner.",
                    category = NutritionCategory.DINNER,
                    targetGoal = FitnessGoal.LOSE_WEIGHT,
                    calories = 400,
                    protein = 30f,
                    isGlutenFree = true
=======
                    description = "For weight loss, try grilled fish with steamed vegetables and a small portion of sweet potato for a filling yet light dinner.",
                    category = NutritionCategory.DINNER,
                    benefits = "Weight management, lean protein, complex carbs, vegetables",
                    tags = "dinner,fish,vegetables,weight loss"
>>>>>>> 818ab1f (Updated)
                )
            )

            nutritionTipDao.insertTips(defaultTips)
        } catch (e: Exception) {
            // Log error but don't crash
        }
    }
}
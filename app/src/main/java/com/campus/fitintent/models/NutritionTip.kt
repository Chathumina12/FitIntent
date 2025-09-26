package com.campus.fitintent.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Daily nutrition tips and guidance
 */
@Entity(tableName = "nutrition_tips")
data class NutritionTip(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val description: String,
    val category: NutritionCategory,
    val benefits: String? = null, // Additional health benefits
    val difficulty: TipDifficulty = TipDifficulty.EASY,
    val icon: String? = null, // Icon identifier
    val imageUrl: String? = null,
    val tags: String? = null, // Comma-separated tags
    val isActive: Boolean = true,
    val priority: Int = 0, // Higher priority tips shown first
    val seasonalTip: Boolean = false, // Seasonal relevance
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class NutritionCategory {
    HYDRATION,
    PROTEIN,
    VITAMINS,
    FIBER,
    HEALTHY_FATS,
    CARBOHYDRATES,
    MINERALS,
    WEIGHT_MANAGEMENT,
    MEAL_TIMING,
    FOOD_PREP,
    SUPPLEMENTS,
    GENERAL,
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    HEALTHY_TIPS,
    PRE_WORKOUT,
    POST_WORKOUT
}

enum class TipDifficulty {
    EASY,
    MEDIUM,
    ADVANCED
}

/**
 * User-specific favorites and interaction with nutrition tips
 */
@Entity(
    tableName = "user_nutrition_favorites",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NutritionTip::class,
            parentColumns = ["id"],
            childColumns = ["tipId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index("tipId"),
        Index(value = ["userId", "tipId"], unique = true)
    ]
)
data class UserNutritionFavorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val tipId: Long,
    val isFavorite: Boolean = true,
    val hasRead: Boolean = false,
    val addedAt: Date = Date()
)

/**
 * Daily nutrition tip rotation tracking
 */
@Entity(
    tableName = "daily_nutrition_tips",
    foreignKeys = [
        ForeignKey(
            entity = NutritionTip::class,
            parentColumns = ["id"],
            childColumns = ["tipId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("tipId"),
        Index(value = ["date"], unique = true)
    ]
)
data class DailyNutritionTip(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val tipId: Long,
    val date: Date,
    val isActive: Boolean = true
)

/**
 * Pre-defined nutrition tips data for seeding database
 */
object NutritionTipsSeedData {
    fun getPreloadedTips(): List<NutritionTip> {
        return listOf(
            NutritionTip(
                title = "Start Your Day with Water",
                description = "Drink a glass of water immediately after waking up to kickstart your metabolism and rehydrate your body after hours of sleep.",
                category = NutritionCategory.HYDRATION,
                benefits = "Boosts metabolism, improves brain function, helps with weight management",
                difficulty = TipDifficulty.EASY,
                tags = "morning,hydration,metabolism",
                priority = 10
            ),
            NutritionTip(
                title = "Include Protein in Every Meal",
                description = "Aim to include a quality protein source in each meal to maintain muscle mass, support recovery, and keep you feeling full longer.",
                category = NutritionCategory.PROTEIN,
                benefits = "Muscle maintenance, satiety, stable blood sugar levels",
                difficulty = TipDifficulty.EASY,
                tags = "protein,muscle,satiety",
                priority = 9
            ),
            NutritionTip(
                title = "Eat the Rainbow",
                description = "Include colorful fruits and vegetables in your diet to ensure you're getting a wide variety of vitamins, minerals, and antioxidants.",
                category = NutritionCategory.VITAMINS,
                benefits = "Rich in antioxidants, supports immune system, reduces inflammation",
                difficulty = TipDifficulty.EASY,
                tags = "fruits,vegetables,antioxidants,vitamins",
                priority = 8
            ),
            NutritionTip(
                title = "Choose Whole Grains",
                description = "Opt for whole grain versions of bread, rice, and pasta to increase fiber intake and maintain stable energy levels throughout the day.",
                category = NutritionCategory.FIBER,
                benefits = "Better digestion, sustained energy, heart health",
                difficulty = TipDifficulty.EASY,
                tags = "fiber,whole grains,energy,digestion",
                priority = 7
            ),
            NutritionTip(
                title = "Don't Skip Breakfast",
                description = "Start your day with a balanced breakfast containing protein, healthy fats, and complex carbs to fuel your body and brain.",
                category = NutritionCategory.MEAL_TIMING,
                benefits = "Better focus, stable energy, improved metabolism",
                difficulty = TipDifficulty.EASY,
                tags = "breakfast,meal timing,energy,focus",
                priority = 8
            ),
            NutritionTip(
                title = "Practice Mindful Eating",
                description = "Eat slowly and pay attention to your food. This helps with digestion, prevents overeating, and increases meal satisfaction.",
                category = NutritionCategory.GENERAL,
                benefits = "Better digestion, portion control, increased satisfaction",
                difficulty = TipDifficulty.MEDIUM,
                tags = "mindfulness,digestion,portion control",
                priority = 6
            ),
            NutritionTip(
                title = "Stay Hydrated During Workouts",
                description = "Drink water before, during, and after exercise to maintain performance and aid recovery. Add electrolytes for intense sessions.",
                category = NutritionCategory.HYDRATION,
                benefits = "Better performance, faster recovery, prevents dehydration",
                difficulty = TipDifficulty.MEDIUM,
                tags = "hydration,workout,recovery,electrolytes",
                priority = 7
            ),
            NutritionTip(
                title = "Include Healthy Fats Daily",
                description = "Add sources of healthy fats like avocados, nuts, seeds, and olive oil to support brain health and nutrient absorption.",
                category = NutritionCategory.HEALTHY_FATS,
                benefits = "Brain health, nutrient absorption, hormone production",
                difficulty = TipDifficulty.EASY,
                tags = "healthy fats,brain health,nuts,avocado",
                priority = 7
            ),
            NutritionTip(
                title = "Prep Your Meals",
                description = "Spend time on weekends preparing healthy meals and snacks for the week to avoid impulsive food choices.",
                category = NutritionCategory.FOOD_PREP,
                benefits = "Saves time, healthier choices, portion control, cost-effective",
                difficulty = TipDifficulty.MEDIUM,
                tags = "meal prep,planning,healthy choices",
                priority = 6
            ),
            NutritionTip(
                title = "Listen to Your Body",
                description = "Pay attention to hunger and fullness cues. Eat when hungry and stop when satisfied, not stuffed.",
                category = NutritionCategory.GENERAL,
                benefits = "Better relationship with food, natural portion control",
                difficulty = TipDifficulty.MEDIUM,
                tags = "intuitive eating,hunger cues,mindfulness",
                priority = 5
            ),
            NutritionTip(
                title = "Limit Processed Foods",
                description = "Choose whole, minimally processed foods over packaged and processed options to reduce sodium, added sugars, and unhealthy fats.",
                category = NutritionCategory.GENERAL,
                benefits = "Better nutrition quality, reduced inflammation, heart health",
                difficulty = TipDifficulty.MEDIUM,
                tags = "whole foods,processed foods,clean eating",
                priority = 8
            ),
            NutritionTip(
                title = "Post-Workout Nutrition",
                description = "Eat a combination of protein and carbs within 30-60 minutes after exercise to optimize recovery and muscle growth.",
                category = NutritionCategory.MEAL_TIMING,
                benefits = "Faster recovery, muscle growth, glycogen replenishment",
                difficulty = TipDifficulty.MEDIUM,
                tags = "post workout,recovery,protein,carbs",
                priority = 7
            ),
            NutritionTip(
                title = "Get Enough Fiber",
                description = "Aim for 25-35g of fiber daily from fruits, vegetables, legumes, and whole grains to support digestive health.",
                category = NutritionCategory.FIBER,
                benefits = "Better digestion, stable blood sugar, heart health",
                difficulty = TipDifficulty.EASY,
                tags = "fiber,digestion,fruits,vegetables",
                priority = 6
            ),
            NutritionTip(
                title = "Don't Eliminate Food Groups",
                description = "Unless medically necessary, avoid eliminating entire food groups. Balance and moderation lead to sustainable healthy eating.",
                category = NutritionCategory.GENERAL,
                benefits = "Sustainable eating, balanced nutrition, better relationship with food",
                difficulty = TipDifficulty.EASY,
                tags = "balance,moderation,sustainable eating",
                priority = 5
            ),
            NutritionTip(
                title = "Time Your Carbs",
                description = "Consume most of your carbohydrates around your workouts when your body can best utilize them for energy and recovery.",
                category = NutritionCategory.CARBOHYDRATES,
                benefits = "Better energy utilization, improved performance, body composition",
                difficulty = TipDifficulty.ADVANCED,
                tags = "carb timing,workout nutrition,performance",
                priority = 4
            ),
            NutritionTip(
                title = "Quality Over Quantity",
                description = "Focus on nutrient-dense foods that provide maximum nutritional value per calorie rather than just counting calories.",
                category = NutritionCategory.GENERAL,
                benefits = "Better nutrition quality, increased satiety, improved health markers",
                difficulty = TipDifficulty.MEDIUM,
                tags = "nutrient density,food quality,health",
                priority = 6
            ),
            NutritionTip(
                title = "Stay Consistent, Not Perfect",
                description = "Aim for consistency in your eating habits rather than perfection. Small, sustainable changes lead to long-term success.",
                category = NutritionCategory.GENERAL,
                benefits = "Sustainable habits, reduced stress, long-term success",
                difficulty = TipDifficulty.EASY,
                tags = "consistency,sustainability,habits,mindset",
                priority = 7
            ),
            NutritionTip(
                title = "Support Your Immune System",
                description = "Include vitamin C-rich foods, zinc sources, and probiotics in your diet to keep your immune system strong.",
                category = NutritionCategory.VITAMINS,
                benefits = "Stronger immune system, better recovery, reduced illness",
                difficulty = TipDifficulty.MEDIUM,
                tags = "immune system,vitamin C,zinc,probiotics",
                priority = 6
            ),
            NutritionTip(
                title = "Plan Your Snacks",
                description = "Prepare healthy snacks in advance to avoid reaching for processed options when hunger strikes between meals.",
                category = NutritionCategory.FOOD_PREP,
                benefits = "Better food choices, stable energy, cost savings",
                difficulty = TipDifficulty.EASY,
                tags = "snacks,planning,healthy choices,preparation",
                priority = 5
            ),
            NutritionTip(
                title = "Understand Portion Sizes",
                description = "Learn proper portion sizes for different food groups. Use your hand as a guide: palm for protein, cupped hand for carbs.",
                category = NutritionCategory.GENERAL,
                benefits = "Better portion control, weight management, balanced nutrition",
                difficulty = TipDifficulty.MEDIUM,
                tags = "portion control,serving sizes,weight management",
                priority = 6
            )
        )
    }

    /**
     * Get tip for specific day (rotation system)
     */
    fun getTipForDay(dayOfYear: Int): NutritionTip {
        val tips = getPreloadedTips()
        return tips[dayOfYear % tips.size]
    }

    /**
     * Get tips by category
     */
    fun getTipsByCategory(category: NutritionCategory): List<NutritionTip> {
        return getPreloadedTips().filter { it.category == category }
    }
}
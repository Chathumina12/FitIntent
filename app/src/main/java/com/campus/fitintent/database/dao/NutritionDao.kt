package com.campus.fitintent.database.dao

import androidx.room.*
import com.campus.fitintent.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for NutritionTip operations
 */
@Dao
interface NutritionTipDao {
    @Query("SELECT * FROM nutrition_tips ORDER BY createdAt DESC")
    fun getAllTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE category = :category")
    fun getTipsByCategory(category: NutritionCategory): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE category = :category")
    fun getTipsByGoal(category: NutritionCategory): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE id = :tipId")
    suspend fun getTipById(tipId: Long): NutritionTip?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: NutritionTip): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTips(tips: List<NutritionTip>)

    @Update
    suspend fun updateTip(tip: NutritionTip)

    @Delete
    suspend fun deleteTip(tip: NutritionTip)

    @Query("""
        SELECT * FROM nutrition_tips
        WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY createdAt DESC
    """)
    fun searchTips(query: String): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%vegetarian%'")
    fun getVegetarianTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%vegan%'")
    fun getVeganTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%gluten-free%'")
    fun getGlutenFreeTips(): Flow<List<NutritionTip>>

    @Query("""
        SELECT * FROM nutrition_tips
        WHERE category IN (:categories)
        AND isActive = 1
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getDailyTip(categories: List<NutritionCategory>): NutritionTip?

    @Query("SELECT COUNT(*) FROM nutrition_tips")
    suspend fun getTipCount(): Int
}

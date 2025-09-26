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

<<<<<<< HEAD
    @Query("SELECT * FROM nutrition_tips WHERE targetGoal = :goal OR targetGoal IS NULL")
    fun getTipsByGoal(goal: FitnessGoal): Flow<List<NutritionTip>>
=======
    @Query("SELECT * FROM nutrition_tips WHERE category = :category")
    fun getTipsByGoal(category: NutritionCategory): Flow<List<NutritionTip>>
>>>>>>> 818ab1f (Updated)

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
<<<<<<< HEAD
        WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')
=======
        WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
>>>>>>> 818ab1f (Updated)
        ORDER BY createdAt DESC
    """)
    fun searchTips(query: String): Flow<List<NutritionTip>>

<<<<<<< HEAD
    @Query("SELECT * FROM nutrition_tips WHERE isVegetarian = 1")
    fun getVegetarianTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE isVegan = 1")
    fun getVeganTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE isGlutenFree = 1")
=======
    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%vegetarian%'")
    fun getVegetarianTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%vegan%'")
    fun getVeganTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE tags LIKE '%gluten-free%'")
>>>>>>> 818ab1f (Updated)
    fun getGlutenFreeTips(): Flow<List<NutritionTip>>

    @Query("""
        SELECT * FROM nutrition_tips
        WHERE category IN (:categories)
<<<<<<< HEAD
        AND (targetGoal = :goal OR targetGoal IS NULL)
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getDailyTip(categories: List<NutritionCategory>, goal: FitnessGoal?): NutritionTip?
=======
        AND isActive = 1
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getDailyTip(categories: List<NutritionCategory>): NutritionTip?
>>>>>>> 818ab1f (Updated)

    @Query("SELECT COUNT(*) FROM nutrition_tips")
    suspend fun getTipCount(): Int
}
<<<<<<< HEAD

/**
 * Data Access Object for QuizAnswer operations
 */
@Dao
interface QuizAnswerDao {
    @Query("SELECT * FROM quiz_answers WHERE userId = :userId ORDER BY answeredAt")
    suspend fun getAnswersByUser(userId: Long): List<QuizAnswer>

    @Query("SELECT * FROM quiz_answers WHERE userId = :userId AND questionId = :questionId LIMIT 1")
    suspend fun getAnswerByQuestion(userId: Long, questionId: String): QuizAnswer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: QuizAnswer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<QuizAnswer>)

    @Update
    suspend fun updateAnswer(answer: QuizAnswer)

    @Query("DELETE FROM quiz_answers WHERE userId = :userId")
    suspend fun deleteUserAnswers(userId: Long)

    @Query("SELECT COUNT(*) FROM quiz_answers WHERE userId = :userId")
    suspend fun getAnswerCount(userId: Long): Int
}
=======
>>>>>>> 818ab1f (Updated)

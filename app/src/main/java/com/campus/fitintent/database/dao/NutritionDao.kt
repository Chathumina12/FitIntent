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

    @Query("SELECT * FROM nutrition_tips WHERE targetGoal = :goal OR targetGoal IS NULL")
    fun getTipsByGoal(goal: FitnessGoal): Flow<List<NutritionTip>>

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
        WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')
        ORDER BY createdAt DESC
    """)
    fun searchTips(query: String): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE isVegetarian = 1")
    fun getVegetarianTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE isVegan = 1")
    fun getVeganTips(): Flow<List<NutritionTip>>

    @Query("SELECT * FROM nutrition_tips WHERE isGlutenFree = 1")
    fun getGlutenFreeTips(): Flow<List<NutritionTip>>

    @Query("""
        SELECT * FROM nutrition_tips
        WHERE category IN (:categories)
        AND (targetGoal = :goal OR targetGoal IS NULL)
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getDailyTip(categories: List<NutritionCategory>, goal: FitnessGoal?): NutritionTip?

    @Query("SELECT COUNT(*) FROM nutrition_tips")
    suspend fun getTipCount(): Int
}

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
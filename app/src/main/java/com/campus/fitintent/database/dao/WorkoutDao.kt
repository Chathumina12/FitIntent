package com.campus.fitintent.database.dao

import androidx.room.*
import com.campus.fitintent.models.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for Workout operations
 */
@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE isCustom = 0 ORDER BY category, difficulty")
    fun getAllPredefinedWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE category = :category AND isCustom = 0")
    fun getWorkoutsByCategory(category: WorkoutCategory): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE difficulty = :difficulty AND isCustom = 0")
    fun getWorkoutsByDifficulty(difficulty: DifficultyLevel): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE createdBy = :userId AND isCustom = 1")
    fun getCustomWorkouts(userId: Long): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): Workout?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("""
        SELECT * FROM workouts
        WHERE (name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        AND isCustom = 0
        ORDER BY name
    """)
    fun searchWorkouts(query: String): Flow<List<Workout>>

    @Query("SELECT DISTINCT category FROM workouts WHERE isCustom = 0")
    suspend fun getAllCategories(): List<WorkoutCategory>

    @Query("SELECT COUNT(*) FROM workouts WHERE createdBy = :userId AND isCustom = 1")
    suspend fun getCustomWorkoutCount(userId: Long): Int
}

/**
 * Data Access Object for Exercise operations
 */
@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId ORDER BY orderIndex")
    suspend fun getExercisesByWorkout(workoutId: Long): List<Exercise>

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId ORDER BY orderIndex")
    fun getExercisesByWorkoutFlow(workoutId: Long): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercises WHERE workoutId = :workoutId")
    suspend fun deleteExercisesByWorkout(workoutId: Long)
}

/**
 * Data Access Object for WorkoutSession operations
 */
@Dao
interface WorkoutSessionDao {
    @Query("SELECT * FROM workout_sessions WHERE userId = :userId ORDER BY startTime DESC")
    fun getSessionsByUser(userId: Long): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND DATE(startTime / 1000, 'unixepoch') = DATE(:date / 1000, 'unixepoch')")
    suspend fun getSessionsByUserAndDate(userId: Long, date: Date): List<WorkoutSession>

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Long): WorkoutSession?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WorkoutSession): Long

    @Update
    suspend fun updateSession(session: WorkoutSession)

    @Query("""
        UPDATE workout_sessions
        SET endTime = :endTime,
            durationMinutes = :duration,
            caloriesBurned = :calories,
            isCompleted = :isCompleted,
            completionPercentage = :percentage
        WHERE id = :sessionId
    """)
    suspend fun completeSession(
        sessionId: Long,
        endTime: Date,
        duration: Int,
        calories: Int,
        isCompleted: Boolean,
        percentage: Float
    )

    @Delete
    suspend fun deleteSession(session: WorkoutSession)

    @Query("""
        SELECT COUNT(*) FROM workout_sessions
        WHERE userId = :userId
        AND isCompleted = 1
    """)
    suspend fun getCompletedSessionCount(userId: Long): Int

    @Query("""
        SELECT SUM(durationMinutes) FROM workout_sessions
        WHERE userId = :userId
        AND isCompleted = 1
    """)
    suspend fun getTotalWorkoutMinutes(userId: Long): Int?

    @Query("""
        SELECT SUM(caloriesBurned) FROM workout_sessions
        WHERE userId = :userId
        AND isCompleted = 1
    """)
    suspend fun getTotalCaloriesBurned(userId: Long): Int?

    @Query("""
        SELECT * FROM workout_sessions
        WHERE userId = :userId
        AND startTime BETWEEN :startDate AND :endDate
        ORDER BY startTime DESC
    """)
    fun getSessionsInDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<WorkoutSession>>

    @Query("""
        SELECT AVG(feelingRating) FROM workout_sessions
        WHERE userId = :userId
        AND isCompleted = 1
        AND feelingRating IS NOT NULL
    """)
    suspend fun getAverageFeelingRating(userId: Long): Float?
}
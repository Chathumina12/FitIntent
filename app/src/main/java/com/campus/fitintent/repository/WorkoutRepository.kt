package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.ExerciseDao
import com.campus.fitintent.database.dao.WorkoutDao
import com.campus.fitintent.database.dao.WorkoutSessionDao
import com.campus.fitintent.models.*
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Repository for Workout-related operations
 */
class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val workoutSessionDao: WorkoutSessionDao,
    private val exerciseDao: ExerciseDao
) {
    /**
     * Get all predefined workouts
     */
    fun getAllWorkouts(): Flow<List<Workout>> {
        return workoutDao.getAllPredefinedWorkouts()
    }

    /**
     * Get workouts by category
     */
    fun getWorkoutsByCategory(category: WorkoutCategory): Flow<List<Workout>> {
        return workoutDao.getWorkoutsByCategory(category)
    }

    /**
     * Search workouts
     */
    fun searchWorkouts(query: String): Flow<List<Workout>> {
        return workoutDao.searchWorkouts(query)
    }

    /**
     * Start a workout session
     */
    suspend fun startWorkoutSession(
        userId: Long,
        workoutId: Long
    ): Result<WorkoutSession> = withContext(Dispatchers.IO) {
        try {
            val workout = workoutDao.getWorkoutById(workoutId)
                ?: return@withContext Result.Error("Workout not found")

            val session = WorkoutSession(
                userId = userId,
                workoutId = workoutId,
                startTime = Date(),
                isCompleted = false,
                completionPercentage = 0f
            )

            val sessionId = workoutSessionDao.insertSession(session)
            Result.Success(session.copy(id = sessionId))
        } catch (e: Exception) {
            Result.Error("Failed to start workout: ${e.message}")
        }
    }

    /**
     * Complete a workout session
     */
    suspend fun completeWorkoutSession(
        sessionId: Long,
        caloriesBurned: Int,
        feelingRating: Int? = null,
        difficultyRating: Int? = null,
        notes: String? = null
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val session = workoutSessionDao.getSessionById(sessionId)
                ?: return@withContext Result.Error("Session not found")

            val endTime = Date()
            val duration = ((endTime.time - session.startTime.time) / 1000 / 60).toInt()

            workoutSessionDao.completeSession(
                sessionId = sessionId,
                endTime = endTime,
                duration = duration,
                calories = caloriesBurned,
                isCompleted = true,
                percentage = 100f
            )

            // Update with additional details
            val updatedSession = session.copy(
                endTime = endTime,
                durationMinutes = duration,
                caloriesBurned = caloriesBurned,
                feelingRating = feelingRating,
                difficultyRating = difficultyRating,
                notes = notes,
                isCompleted = true,
                completionPercentage = 100f
            )
            workoutSessionDao.updateSession(updatedSession)

            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to complete workout: ${e.message}")
        }
    }

    /**
     * Get workout sessions for user
     */
    fun getUserSessions(userId: Long): Flow<List<WorkoutSession>> {
        return workoutSessionDao.getSessionsByUser(userId)
    }

    /**
     * Get workout statistics
     */
    suspend fun getWorkoutStats(userId: Long): Result<WorkoutStats> = withContext(Dispatchers.IO) {
        try {
            val totalWorkouts = workoutSessionDao.getCompletedSessionCount(userId)
            val totalMinutes = workoutSessionDao.getTotalWorkoutMinutes(userId) ?: 0
            val totalCalories = workoutSessionDao.getTotalCaloriesBurned(userId) ?: 0
            val avgFeeling = workoutSessionDao.getAverageFeelingRating(userId) ?: 0f

            Result.Success(
                WorkoutStats(
                    totalWorkouts = totalWorkouts,
                    totalMinutes = totalMinutes,
                    totalCaloriesBurned = totalCalories,
                    averageFeelingRating = avgFeeling
                )
            )
        } catch (e: Exception) {
            Result.Error("Failed to get workout stats: ${e.message}")
        }
    }

    /**
     * Create custom workout
     */
    suspend fun createCustomWorkout(
        userId: Long,
        name: String,
        description: String,
        category: WorkoutCategory,
        difficulty: DifficultyLevel,
        exercises: List<Exercise>
    ): Result<Workout> = withContext(Dispatchers.IO) {
        try {
            if (name.isBlank()) {
                return@withContext Result.Error("Workout name is required")
            }

            val workout = Workout(
                name = name,
                description = description,
                category = category,
                difficulty = difficulty,
                durationMinutes = exercises.sumOf { it.durationSeconds ?: 0 } / 60,
                caloriesBurned = 0, // Will be calculated based on exercises
                isCustom = true,
                createdBy = userId,
                instructions = "Custom workout"
            )

            val workoutId = workoutDao.insertWorkout(workout)

            // Add exercises
            val exercisesWithWorkoutId = exercises.mapIndexed { index, exercise ->
                exercise.copy(
                    workoutId = workoutId,
                    orderIndex = index
                )
            }
            exerciseDao.insertExercises(exercisesWithWorkoutId)

            Result.Success(workout.copy(id = workoutId))
        } catch (e: Exception) {
            Result.Error("Failed to create custom workout: ${e.message}")
        }
    }
}

data class WorkoutStats(
    val totalWorkouts: Int,
    val totalMinutes: Int,
    val totalCaloriesBurned: Int,
    val averageFeelingRating: Float
)
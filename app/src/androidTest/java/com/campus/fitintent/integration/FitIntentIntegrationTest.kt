package com.campus.fitintent.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.campus.fitintent.database.FitIntentDatabase
import com.campus.fitintent.database.dao.*
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.*
import com.campus.fitintent.utils.*
import com.campus.fitintent.viewmodels.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Comprehensive integration tests for FitIntent application
 * Tests end-to-end flows including database operations, repository layers, and ViewModels
 */
@RunWith(AndroidJUnit4::class)
class FitIntentIntegrationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FitIntentDatabase
    private lateinit var userRepository: UserRepository
    private lateinit var habitRepository: HabitRepository
    private lateinit var workoutRepository: WorkoutRepository
    private lateinit var progressRepository: ProgressRepository
    private lateinit var nutritionRepository: NutritionRepository

    private lateinit var authViewModel: AuthViewModel
    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var badgeManager: BadgeManager

    private val testUserId = 1L
    private val testEmail = "integration@test.com"
    private val testPassword = "TestPassword123!"
    private val testName = "Integration Test User"

    @Before
    fun setup() {
        // Create in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FitIntentDatabase::class.java
        ).allowMainThreadQueries().build()

        // Initialize repositories
        userRepository = UserRepository(database.userDao())
        habitRepository = HabitRepository(
            database.habitDao(),
            database.habitCompletionDao(),
            database.streakDao()
        )
        workoutRepository = WorkoutRepository(
            database.workoutDao(),
            database.exerciseDao(),
            database.workoutSessionDao()
        )
        progressRepository = ProgressRepository(
            database.userBadgeDao(),
            database.badgeDao(),
            database.dailyGoalDao(),
            database.streakDao()
        )
        nutritionRepository = NutritionRepository(database.nutritionTipDao())

        // Initialize utility classes
        badgeManager = BadgeManager(progressRepository)

        // Initialize ViewModels
        authViewModel = AuthViewModel(userRepository)
        dashboardViewModel = DashboardViewModel(
            habitRepository = habitRepository,
            workoutRepository = workoutRepository,
            nutritionRepository = nutritionRepository,
            progressRepository = progressRepository,
            userRepository = userRepository
        )
    }

    @After
    fun cleanup() {
        database.close()
    }

    // ========================
    // User Authentication Integration Tests
    // ========================

    @Test
    fun `complete user authentication flow integration`() = runTest {
        // Test signup flow
        authViewModel.signup(testName, testEmail, testPassword, testPassword, true)

        // Verify user creation in database
        val createdUser = database.userDao().getUserByEmail(testEmail)
        assertNotNull("User should be created in database", createdUser)
        assertEquals("User email should match", testEmail, createdUser?.email)
        assertTrue("Password should be hashed", createdUser?.passwordHash != testPassword)

        // Test login flow
        authViewModel.login(testEmail, testPassword, true)

        // Verify session handling
        val sessionExists = userRepository.hasActiveSession()
        assertTrue("User session should be active", sessionExists)
    }

    @Test
    fun `user onboarding quiz integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Update user with quiz answers
        val updatedUser = user.copy(
            id = userId,
            mainGoal = MainGoal.LOSE_WEIGHT,
            exerciseFrequency = ExerciseFrequency.WEEKLY_3_5,
            workoutPreference = WorkoutPreference.STRENGTH,
            fitnessLevel = FitnessLevel.INTERMEDIATE,
            timeAvailable = TimeAvailable.MIN_30_45,
            hasCompletedOnboarding = true
        )

        val updateResult = userRepository.updateUser(updatedUser)
        assertTrue("User update should succeed", updateResult.isSuccess)

        // Verify quiz completion
        val retrievedUser = database.userDao().getById(userId)
        assertNotNull("Updated user should exist", retrievedUser)
        assertEquals("Main goal should be set", MainGoal.LOSE_WEIGHT, retrievedUser?.mainGoal)
        assertTrue("Onboarding should be completed", retrievedUser?.hasCompletedOnboarding == true)
    }

    // ========================
    // Habit System Integration Tests
    // ========================

    @Test
    fun `complete habit lifecycle integration`() = runTest {
        // Create user and habit
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        val habit = createTestHabit(userId)
        val habitResult = habitRepository.createHabit(habit)
        assertTrue("Habit creation should succeed", habitResult.isSuccess)
        val habitId = habitResult.getOrNull()!!

        // Complete habit and verify streak calculation
        val completionResult = habitRepository.completeHabit(habitId, Date())
        assertTrue("Habit completion should succeed", completionResult.isSuccess)

        // Verify habit completion in database
        val completion = database.habitCompletionDao().getByHabitAndDate(
            habitId,
            DateUtils.getStartOfDay(Date()),
            DateUtils.getEndOfDay(Date())
        )
        assertNotNull("Completion should be recorded", completion)
        assertTrue("Completion should be marked as true", completion?.isCompleted == true)

        // Verify streak creation
        val streak = database.streakDao().getByHabitId(habitId)
        assertNotNull("Streak should be created", streak)
        assertEquals("Streak should be 1", 1, streak?.currentStreak)
    }

    @Test
    fun `habit streak bonus and badge integration`() = runTest {
        // Create user and habit
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        val habit = createTestHabit(userId)
        val habitId = habitRepository.createHabit(habit).getOrNull()!!

        // Initialize badges in database
        val allBadges = badgeManager.getAllBadges()
        allBadges.forEach { badge ->
            database.badgeDao().insert(badge)
            database.userBadgeDao().insert(
                UserBadge(
                    userId = userId,
                    badgeId = badge.id,
                    isUnlocked = false,
                    unlockedAt = null
                )
            )
        }

        // Complete habit multiple times to build streak
        repeat(7) { day ->
            val date = Date(System.currentTimeMillis() - (6 - day) * 86400000L) // Last 7 days
            habitRepository.completeHabit(habitId, date)
        }

        // Check badge progress after habit completions
        badgeManager.checkBadgeProgress(userId, BadgeActivity.HABIT_COMPLETED, 7)

        // Verify streak milestone points
        val totalPoints = PointCalculator.calculateStreakMilestonePoints(7)
        assertEquals("7-day streak should give 50 points", 50, totalPoints)

        // Verify badge progress in database
        val userBadges = database.userBadgeDao().getUserBadges(userId).first()
        val habitBadges = userBadges.filter { badge ->
            allBadges.find { it.id == badge.badgeId }?.type == BadgeType.HABIT
        }
        assertTrue("Should have habit badge progress", habitBadges.isNotEmpty())
    }

    // ========================
    // Workout System Integration Tests
    // ========================

    @Test
    fun `workout completion and points integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Create workout
        val workout = createTestWorkout()
        val workoutId = database.workoutDao().insert(workout)

        // Start workout session
        val session = WorkoutSession(
            userId = userId,
            workoutId = workoutId,
            startTime = Date(),
            endTime = null,
            isCompleted = false,
            caloriesBurned = 0,
            notes = null
        )
        val sessionId = database.workoutSessionDao().insert(session)

        // Complete workout session
        val completedSession = session.copy(
            id = sessionId,
            endTime = Date(),
            isCompleted = true,
            caloriesBurned = workout.durationMinutes * workout.caloriesPerMinute
        )
        database.workoutSessionDao().update(completedSession)

        // Calculate workout points
        val workoutPoints = PointCalculator.calculateWorkoutPoints(workout)
        assertTrue("Workout should earn points", workoutPoints > 0)

        // Verify session completion
        val retrievedSession = database.workoutSessionDao().getById(sessionId)
        assertTrue("Session should be completed", retrievedSession?.isCompleted == true)
        assertEquals("Calories should be calculated correctly",
            workout.durationMinutes * workout.caloriesPerMinute,
            retrievedSession?.caloriesBurned)
    }

    // ========================
    // Dashboard Integration Tests
    // ========================

    @Test
    fun `dashboard data aggregation integration`() = runTest {
        // Create user with habits and workout history
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Create multiple habits
        repeat(3) { i ->
            val habit = createTestHabit(userId, "Test Habit ${i + 1}")
            val habitId = habitRepository.createHabit(habit).getOrNull()!!

            // Complete some habits
            if (i < 2) {
                habitRepository.completeHabit(habitId, Date())
            }
        }

        // Create workout sessions
        val workout = createTestWorkout()
        val workoutId = database.workoutDao().insert(workout)

        val session = WorkoutSession(
            userId = userId,
            workoutId = workoutId,
            startTime = Date(System.currentTimeMillis() - 86400000), // Yesterday
            endTime = Date(System.currentTimeMillis() - 86400000 + 1800000), // 30 min later
            isCompleted = true,
            caloriesBurned = 300,
            notes = "Great workout!"
        )
        database.workoutSessionDao().insert(session)

        // Load dashboard data
        dashboardViewModel.loadDashboardData(userId)

        // Verify dashboard aggregates data correctly
        val habits = habitRepository.getUserHabits(userId).first()
        assertEquals("Should have 3 habits", 3, habits.size)

        val recentWorkouts = workoutRepository.getRecentWorkouts(userId, 5).first()
        assertEquals("Should have 1 recent workout", 1, recentWorkouts.size)
    }

    // ========================
    // Nutrition System Integration Tests
    // ========================

    @Test
    fun `nutrition tip rotation integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Seed nutrition tips
        val tips = NutritionTip.getPreloadedTips()
        tips.forEach { tip ->
            database.nutritionTipDao().insert(tip)
        }

        // Get today's tip
        val todaysTip = nutritionRepository.getDailyTip().first()
        assertNotNull("Should have today's tip", todaysTip)

        // Mark tip as favorite
        val favoriteResult = nutritionRepository.markTipAsFavorite(userId, todaysTip!!.id)
        assertTrue("Marking as favorite should succeed", favoriteResult.isSuccess)

        // Verify tip categories
        val categories = tips.map { it.category }.toSet()
        assertTrue("Should have multiple categories", categories.size > 5)
        assertTrue("Should include hydration tips",
            categories.contains(NutritionCategory.HYDRATION))
    }

    // ========================
    // Points and Gamification Integration Tests
    // ========================

    @Test
    fun `complete gamification system integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Initialize user progress
        database.userBadgeDao().insert(
            UserBadge(userId = userId, badgeId = 1L, isUnlocked = false, unlockedAt = null)
        )

        // Complete various activities
        var totalPoints = 0

        // Complete habit
        val habit = createTestHabit(userId)
        val habitId = habitRepository.createHabit(habit).getOrNull()!!
        habitRepository.completeHabit(habitId, Date())
        totalPoints += PointCalculator.calculateHabitPoints(habit)

        // Complete workout
        val workout = createTestWorkout()
        totalPoints += PointCalculator.calculateWorkoutPoints(workout)

        // Calculate level progress
        val level = PointCalculator.calculateUserLevel(totalPoints)
        val levelProgress = PointCalculator.calculateLevelProgress(totalPoints)

        assertTrue("Should earn points from activities", totalPoints > 0)
        assertTrue("Level should be calculated", level >= 1)
        assertTrue("Level progress should be valid", levelProgress >= 0.0f && levelProgress <= 1.0f)

        // Test badge system integration
        badgeManager.checkBadgeProgress(userId, BadgeActivity.HABIT_COMPLETED)

        val badgeStats = badgeManager.getBadgeStatistics(userId)
        assertEquals("Should track badge statistics", 24, badgeStats.totalBadges)
        assertTrue("Should have completion percentage", badgeStats.completionPercentage >= 0.0f)
    }

    // ========================
    // Database Transaction Integration Tests
    // ========================

    @Test
    fun `database transaction rollback integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Test transaction rollback scenario
        try {
            database.runInTransaction {
                // Insert habit
                val habit = createTestHabit(userId)
                database.habitDao().insert(habit)

                // Simulate error condition
                throw RuntimeException("Simulated transaction error")
            }
        } catch (e: RuntimeException) {
            // Expected exception
        }

        // Verify rollback - habit should not exist
        val habits = database.habitDao().getActiveHabitsByUser(userId).first()
        assertTrue("Habits should be empty due to rollback", habits.isEmpty())
    }

    // ========================
    // Cross-Repository Data Consistency Tests
    // ========================

    @Test
    fun `cross_repository data consistency integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        // Create habit and complete it
        val habit = createTestHabit(userId)
        val habitId = habitRepository.createHabit(habit).getOrNull()!!
        habitRepository.completeHabit(habitId, Date())

        // Verify data consistency across repositories
        val userHabits = habitRepository.getUserHabits(userId).first()
        val habitCompletions = database.habitCompletionDao().getTotalCompletionsToday(userId)
        val userStreaks = database.streakDao().getByHabitId(habitId)

        assertEquals("Should have 1 habit", 1, userHabits.size)
        assertEquals("Should have 1 completion today", 1, habitCompletions)
        assertNotNull("Should have streak record", userStreaks)
        assertEquals("Streak should be 1", 1, userStreaks?.currentStreak)
    }

    // ========================
    // Performance Integration Tests
    // ========================

    @Test
    fun `database performance with bulk operations integration`() = runTest {
        // Create user
        val user = createTestUser()
        val userId = database.userDao().insert(user)

        val startTime = System.currentTimeMillis()

        // Bulk insert habits
        repeat(50) { i ->
            val habit = createTestHabit(userId, "Bulk Habit $i")
            habitRepository.createHabit(habit)
        }

        // Bulk insert workout sessions
        val workout = createTestWorkout()
        val workoutId = database.workoutDao().insert(workout)

        repeat(30) { i ->
            val session = WorkoutSession(
                userId = userId,
                workoutId = workoutId,
                startTime = Date(System.currentTimeMillis() - i * 86400000L),
                endTime = Date(System.currentTimeMillis() - i * 86400000L + 1800000L),
                isCompleted = true,
                caloriesBurned = 250 + i * 10,
                notes = "Session $i"
            )
            database.workoutSessionDao().insert(session)
        }

        val endTime = System.currentTimeMillis()
        val operationTime = endTime - startTime

        // Verify bulk operations completed in reasonable time (< 5 seconds)
        assertTrue("Bulk operations should complete quickly", operationTime < 5000)

        // Verify data integrity
        val habits = habitRepository.getUserHabits(userId).first()
        val sessions = database.workoutSessionDao().getRecentSessions(userId, 50).first()

        assertEquals("Should have 50 habits", 50, habits.size)
        assertEquals("Should have 30 sessions", 30, sessions.size)
    }

    // ========================
    // Helper Methods
    // ========================

    private fun createTestUser(): User {
        return User(
            email = testEmail,
            username = testEmail.substringBefore("@"),
            passwordHash = org.mindrot.jbcrypt.BCrypt.hashpw(testPassword, org.mindrot.jbcrypt.BCrypt.gensalt(12)),
            fullName = testName,
            profileImageUrl = null,
            createdAt = Date(),
            updatedAt = Date(),
            lastLogin = null,
            isEmailVerified = false,
            isActive = true,
            failedLoginAttempts = 0,
            isLocked = false,
            mainGoal = null,
            exerciseFrequency = null,
            workoutPreference = null,
            fitnessLevel = null,
            timeAvailable = null,
            hasCompletedOnboarding = false
        )
    }

    private fun createTestHabit(userId: Long, name: String = "Test Habit"): Habit {
        return Habit(
            userId = userId,
            name = name,
            description = "Test habit description",
            category = HabitCategory.HEALTH,
            targetFrequency = FrequencyType.DAILY,
            reminderTime = "09:00",
            isActive = true,
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    private fun createTestWorkout(): Workout {
        return Workout(
            id = 0L, // Will be auto-generated
            name = "Test Workout",
            category = WorkoutCategory.STRENGTH,
            difficulty = WorkoutDifficulty.INTERMEDIATE,
            durationMinutes = 30,
            description = "Test workout description",
            imageUrl = null,
            exerciseIds = listOf(1L, 2L),
            isActive = true,
            caloriesPerMinute = 10
        )
    }
}
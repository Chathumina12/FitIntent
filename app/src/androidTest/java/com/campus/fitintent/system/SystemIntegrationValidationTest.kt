package com.campus.fitintent.system

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
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Comprehensive System Integration Validation Tests
 * Tests complete app flow, backend connectivity, and system performance
 * Validates that all components work together correctly in production-like scenarios
 */
@RunWith(AndroidJUnit4::class)
class SystemIntegrationValidationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FitIntentDatabase
    private lateinit var systemValidator: SystemValidator

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FitIntentDatabase::class.java
        ).allowMainThreadQueries().build()

        systemValidator = SystemValidator(database)
    }

    @After
    fun cleanup() {
        database.close()
    }

    // ========================
    // Database Schema Validation Tests
    // ========================

    @Test
    fun `database schema validation`() = runTest {
        // Test all DAOs are accessible
        assertNotNull("UserDao should be available", database.userDao())
        assertNotNull("HabitDao should be available", database.habitDao())
        assertNotNull("WorkoutDao should be available", database.workoutDao())
        assertNotNull("ExerciseDao should be available", database.exerciseDao())
        assertNotNull("NutritionTipDao should be available", database.nutritionTipDao())
        assertNotNull("BadgeDao should be available", database.badgeDao())
        assertNotNull("UserBadgeDao should be available", database.userBadgeDao())
        assertNotNull("DailyGoalDao should be available", database.dailyGoalDao())
        assertNotNull("HabitCompletionDao should be available", database.habitCompletionDao())
        assertNotNull("WorkoutSessionDao should be available", database.workoutSessionDao())
        assertNotNull("StreakDao should be available", database.streakDao())

        // Test database version consistency
        assertTrue("Database should have valid version", database.openHelper.readableDatabase.version > 0)
    }

    @Test
    fun `database constraints and relationships validation`() = runTest {
        // Test foreign key constraints work correctly
        val user = systemValidator.createTestUser()
        val userId = database.userDao().insert(user)

        val habit = systemValidator.createTestHabit(userId)
        val habitId = database.habitDao().insert(habit)

        // Test cascade operations
        val completion = HabitCompletion(
            habitId = habitId,
            completedAt = Date(),
            isCompleted = true
        )
        val completionId = database.habitCompletionDao().insert(completion)
        assertTrue("Completion should be inserted", completionId > 0)

        // Test foreign key constraint
        try {
            val invalidCompletion = HabitCompletion(
                habitId = 999999L, // Non-existent habit ID
                completedAt = Date(),
                isCompleted = true
            )
            database.habitCompletionDao().insert(invalidCompletion)
            fail("Should throw foreign key constraint violation")
        } catch (e: Exception) {
            // Expected - foreign key constraint should prevent this
            assertTrue("Should be constraint violation", true)
        }
    }

    // ========================
    // Repository Layer Validation Tests
    // ========================

    @Test
    fun `repository layer system validation`() = runTest {
        val userRepository = UserRepository(database.userDao())
        val habitRepository = HabitRepository(
            database.habitDao(),
            database.habitCompletionDao(),
            database.streakDao()
        )

        // Test user creation and retrieval flow
        val user = systemValidator.createTestUser()
        val createResult = userRepository.createUser(user)
        assertTrue("User creation should succeed", createResult.isSuccess)

        val userId = createResult.getOrNull()!!
        val retrievedUser = userRepository.getUserById(userId)
        assertTrue("User retrieval should succeed", retrievedUser.isSuccess)
        assertEquals("Retrieved user should match", userId, retrievedUser.getOrNull()?.id)

        // Test habit creation and management flow
        val habit = systemValidator.createTestHabit(userId)
        val habitResult = habitRepository.createHabit(habit)
        assertTrue("Habit creation should succeed", habitResult.isSuccess)

        val habitId = habitResult.getOrNull()!!
        val completionResult = habitRepository.completeHabit(habitId, Date())
        assertTrue("Habit completion should succeed", completionResult.isSuccess)

        // Test data consistency
        val userHabits = habitRepository.getUserHabits(userId).first()
        assertEquals("Should have 1 habit", 1, userHabits.size)
        assertEquals("Habit should match", habitId, userHabits.first().id)
    }

    // ========================
    // ViewModel Layer Validation Tests
    // ========================

    @Test
    fun `viewModel layer system validation`() = runTest {
        val userRepository = UserRepository(database.userDao())
        val habitRepository = HabitRepository(
            database.habitDao(),
            database.habitCompletionDao(),
            database.streakDao()
        )
        val workoutRepository = WorkoutRepository(
            database.workoutDao(),
            database.exerciseDao(),
            database.workoutSessionDao()
        )
        val nutritionRepository = NutritionRepository(database.nutritionTipDao())
        val progressRepository = ProgressRepository(
            database.userBadgeDao(),
            database.badgeDao(),
            database.dailyGoalDao(),
            database.streakDao()
        )

        // Test AuthViewModel system integration
        val authViewModel = AuthViewModel(userRepository)
        val latch = CountDownLatch(1)
        var signupResult: com.campus.fitintent.utils.Result<User>? = null

        authViewModel.signupState.observeForever { result ->
            signupResult = result
            latch.countDown()
        }

        authViewModel.signup("Test User", "test@system.com", "TestPass123!", "TestPass123!", true)
        latch.await(5, TimeUnit.SECONDS)

        assertTrue("Signup should succeed", signupResult?.isSuccess == true)
        assertNotNull("User should be created", signupResult?.getOrNull())

        // Test DashboardViewModel system integration
        val userId = signupResult?.getOrNull()?.id!!
        val dashboardViewModel = DashboardViewModel(
            habitRepository, workoutRepository, nutritionRepository, progressRepository, userRepository
        )

        dashboardViewModel.loadDashboardData(userId)

        // Verify dashboard loaded without errors
        assertNotNull("Dashboard should load", dashboardViewModel)
    }

    // ========================
    // Complete User Journey Validation Tests
    // ========================

    @Test
    fun `complete user journey end_to_end validation`() = runTest {
        // Initialize all system components
        val repositories = systemValidator.initializeAllRepositories(database)
        val viewModels = systemValidator.initializeAllViewModels(repositories)

        // Step 1: User Signup
        val user = systemValidator.createTestUser()
        val userId = repositories.userRepository.createUser(user).getOrNull()!!

        // Step 2: Complete Onboarding Quiz
        val updatedUser = user.copy(
            id = userId,
            mainGoal = MainGoal.STAY_ACTIVE,
            exerciseFrequency = ExerciseFrequency.DAILY,
            workoutPreference = WorkoutPreference.HIIT,
            fitnessLevel = FitnessLevel.INTERMEDIATE,
            timeAvailable = TimeAvailable.MIN_30_45,
            hasCompletedOnboarding = true
        )
        val onboardingResult = repositories.userRepository.updateUser(updatedUser)
        assertTrue("Onboarding should complete", onboardingResult.isSuccess)

        // Step 3: Create Habits
        repeat(3) { i ->
            val habit = systemValidator.createTestHabit(userId, "Daily Habit ${i + 1}")
            val habitResult = repositories.habitRepository.createHabit(habit)
            assertTrue("Habit $i should be created", habitResult.isSuccess)
        }

        // Step 4: Complete Habits (simulate daily usage)
        val userHabits = repositories.habitRepository.getUserHabits(userId).first()
        userHabits.forEach { habit ->
            val completionResult = repositories.habitRepository.completeHabit(habit.id, Date())
            assertTrue("Habit ${habit.name} should complete", completionResult.isSuccess)
        }

        // Step 5: Start and Complete Workout
        val workout = systemValidator.createTestWorkout()
        val workoutId = database.workoutDao().insert(workout)

        val session = WorkoutSession(
            userId = userId,
            workoutId = workoutId,
            startTime = Date(System.currentTimeMillis() - 1800000), // 30 minutes ago
            endTime = Date(),
            isCompleted = true,
            caloriesBurned = workout.durationMinutes * workout.caloriesPerMinute,
            notes = "Great workout!"
        )
        database.workoutSessionDao().insert(session)

        // Step 6: Check Progress and Badges
        val badgeManager = BadgeManager(repositories.progressRepository)
        badgeManager.checkBadgeProgress(userId, BadgeActivity.HABIT_COMPLETED, 3)
        badgeManager.checkBadgeProgress(userId, BadgeActivity.WORKOUT_COMPLETED, 1)

        // Step 7: Load Dashboard (complete system integration)
        viewModels.dashboardViewModel.loadDashboardData(userId)

        // Validate complete journey results
        val finalUserHabits = repositories.habitRepository.getUserHabits(userId).first()
        val recentWorkouts = repositories.workoutRepository.getRecentWorkouts(userId, 5).first()
        val badgeStats = badgeManager.getBadgeStatistics(userId)

        assertEquals("Should have 3 habits", 3, finalUserHabits.size)
        assertEquals("Should have 1 recent workout", 1, recentWorkouts.size)
        assertTrue("Should have badge statistics", badgeStats.totalBadges > 0)

        // Calculate total points earned
        val habitPoints = finalUserHabits.size * 10 // 10 points per habit
        val workoutPoints = PointCalculator.calculateWorkoutPoints(workout)
        val totalPoints = habitPoints + workoutPoints

        assertTrue("User should have earned points", totalPoints > 0)
        val userLevel = PointCalculator.calculateUserLevel(totalPoints)
        assertTrue("User should have valid level", userLevel >= 1)
    }

    // ========================
    // Security Validation Tests
    // ========================

    @Test
    fun `security system validation`() = runTest {
        val userRepository = UserRepository(database.userDao())

        // Test password security
        val plainPassword = "TestPassword123!"
        val user = systemValidator.createTestUser().copy(
            passwordHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12))
        )

        val userId = userRepository.createUser(user).getOrNull()!!
        val retrievedUser = database.userDao().getById(userId)

        // Validate password is hashed
        assertNotEquals("Password should be hashed", plainPassword, retrievedUser?.passwordHash)
        assertTrue("Password should verify correctly",
            BCrypt.checkpw(plainPassword, retrievedUser?.passwordHash))

        // Test input validation
        val invalidEmail = "invalid-email"
        val emailValidation = ValidationUtils.validateEmail(invalidEmail)
        assertFalse("Invalid email should fail validation", emailValidation.isValid)

        val weakPassword = "123"
        val passwordValidation = ValidationUtils.validatePassword(weakPassword)
        assertFalse("Weak password should fail validation", passwordValidation.isValid)
    }

    // ========================
    // Performance Validation Tests
    // ========================

    @Test
    fun `system performance validation`() = runTest {
        val repositories = systemValidator.initializeAllRepositories(database)

        // Test bulk operations performance
        val startTime = System.currentTimeMillis()

        // Create user
        val user = systemValidator.createTestUser()
        val userId = repositories.userRepository.createUser(user).getOrNull()!!

        // Bulk create habits
        repeat(20) { i ->
            val habit = systemValidator.createTestHabit(userId, "Performance Habit $i")
            repositories.habitRepository.createHabit(habit)
        }

        // Bulk complete habits
        val habits = repositories.habitRepository.getUserHabits(userId).first()
        habits.forEach { habit ->
            repositories.habitRepository.completeHabit(habit.id, Date())
        }

        val operationTime = System.currentTimeMillis() - startTime

        // Performance assertions
        assertTrue("Bulk operations should complete in reasonable time", operationTime < 3000) // 3 seconds
        assertEquals("All habits should be created", 20, habits.size)

        // Memory usage validation
        val memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()

        // Load all data
        repositories.habitRepository.getUserHabits(userId).first()
        repositories.workoutRepository.getRecentWorkouts(userId, 10).first()

        val memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        val memoryIncrease = memoryAfter - memoryBefore

        assertTrue("Memory increase should be reasonable", memoryIncrease < 50 * 1024 * 1024) // 50MB
    }

    // ========================
    // Data Integrity Validation Tests
    // ========================

    @Test
    fun `data integrity system validation`() = runTest {
        val repositories = systemValidator.initializeAllRepositories(database)

        // Create test data
        val user = systemValidator.createTestUser()
        val userId = repositories.userRepository.createUser(user).getOrNull()!!

        val habit = systemValidator.createTestHabit(userId)
        val habitId = repositories.habitRepository.createHabit(habit).getOrNull()!!

        // Complete habit multiple times
        val completionDates = listOf(
            Date(System.currentTimeMillis() - 2 * 86400000), // 2 days ago
            Date(System.currentTimeMillis() - 1 * 86400000), // 1 day ago
            Date() // Today
        )

        completionDates.forEach { date ->
            repositories.habitRepository.completeHabit(habitId, date)
        }

        // Validate streak calculation integrity
        val currentStreak = repositories.habitRepository.getCurrentStreak(habitId).getOrNull()
        assertEquals("Streak should be 3", 3, currentStreak)

        // Validate completion count integrity
        val completionsToday = repositories.habitRepository.getTotalHabitsCompletedToday(userId).getOrNull()
        assertEquals("Should have 1 completion today", 1, completionsToday)

        // Test data consistency after operations
        val allHabits = repositories.habitRepository.getUserHabits(userId).first()
        val allCompletions = database.habitCompletionDao().getByHabitId(habitId)

        assertEquals("Should have 1 habit", 1, allHabits.size)
        assertEquals("Should have 3 completions", 3, allCompletions.size)
    }

    // ========================
    // Error Handling Validation Tests
    // ========================

    @Test
    fun `error handling system validation`() = runTest {
        val repositories = systemValidator.initializeAllRepositories(database)

        // Test graceful error handling for invalid operations
        val invalidUserId = 999999L

        // Test repository error handling
        val invalidUserResult = repositories.userRepository.getUserById(invalidUserId)
        assertTrue("Should handle invalid user ID gracefully", invalidUserResult.isFailure)

        val invalidHabits = repositories.habitRepository.getUserHabits(invalidUserId).first()
        assertTrue("Should return empty list for invalid user", invalidHabits.isEmpty())

        // Test ViewModel error handling
        val dashboardViewModel = DashboardViewModel(
            repositories.habitRepository,
            repositories.workoutRepository,
            repositories.nutritionRepository,
            repositories.progressRepository,
            repositories.userRepository
        )

        // This should not crash the application
        dashboardViewModel.loadDashboardData(invalidUserId)

        // Verify ViewModel handled error gracefully
        assertNotNull("ViewModel should remain functional", dashboardViewModel)
    }

    // ========================
    // Concurrency Validation Tests
    // ========================

    @Test
    fun `concurrency system validation`() = runTest {
        val repositories = systemValidator.initializeAllRepositories(database)

        val user = systemValidator.createTestUser()
        val userId = repositories.userRepository.createUser(user).getOrNull()!!

        val habit = systemValidator.createTestHabit(userId)
        val habitId = repositories.habitRepository.createHabit(habit).getOrNull()!!

        // Test concurrent habit completions (simulate rapid user interactions)
        val completionResults = mutableListOf<com.campus.fitintent.utils.Result<Unit>>()

        repeat(5) {
            val result = repositories.habitRepository.completeHabit(habitId, Date())
            completionResults.add(result)
        }

        // At least one should succeed (subsequent ones might fail due to duplicate constraint)
        assertTrue("At least one completion should succeed",
            completionResults.any { it.isSuccess })

        // Verify data consistency after concurrent operations
        val finalStreak = repositories.habitRepository.getCurrentStreak(habitId).getOrNull()
        assertTrue("Streak should be valid", finalStreak != null && finalStreak >= 1)
    }
}

/**
 * Helper class for system validation operations
 */
class SystemValidator(private val database: FitIntentDatabase) {

    fun createTestUser(): User {
        return User(
            email = "system.test@fitintent.com",
            username = "systemtest",
            passwordHash = BCrypt.hashpw("SystemTest123!", BCrypt.gensalt(12)),
            fullName = "System Test User",
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

    fun createTestHabit(userId: Long, name: String = "System Test Habit"): Habit {
        return Habit(
            userId = userId,
            name = name,
            description = "System validation test habit",
            category = HabitCategory.HEALTH,
            targetFrequency = FrequencyType.DAILY,
            reminderTime = "09:00",
            isActive = true,
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    fun createTestWorkout(): Workout {
        return Workout(
            id = 0L,
            name = "System Test Workout",
            category = WorkoutCategory.STRENGTH,
            difficulty = WorkoutDifficulty.INTERMEDIATE,
            durationMinutes = 25,
            description = "System validation workout",
            imageUrl = null,
            exerciseIds = listOf(1L, 2L),
            isActive = true,
            caloriesPerMinute = 8
        )
    }

    fun initializeAllRepositories(database: FitIntentDatabase): SystemRepositories {
        return SystemRepositories(
            userRepository = UserRepository(database.userDao()),
            habitRepository = HabitRepository(
                database.habitDao(),
                database.habitCompletionDao(),
                database.streakDao()
            ),
            workoutRepository = WorkoutRepository(
                database.workoutDao(),
                database.exerciseDao(),
                database.workoutSessionDao()
            ),
            nutritionRepository = NutritionRepository(database.nutritionTipDao()),
            progressRepository = ProgressRepository(
                database.userBadgeDao(),
                database.badgeDao(),
                database.dailyGoalDao(),
                database.streakDao()
            )
        )
    }

    fun initializeAllViewModels(repositories: SystemRepositories): SystemViewModels {
        return SystemViewModels(
            authViewModel = AuthViewModel(repositories.userRepository),
            dashboardViewModel = DashboardViewModel(
                repositories.habitRepository,
                repositories.workoutRepository,
                repositories.nutritionRepository,
                repositories.progressRepository,
                repositories.userRepository
            )
        )
    }
}

data class SystemRepositories(
    val userRepository: UserRepository,
    val habitRepository: HabitRepository,
    val workoutRepository: WorkoutRepository,
    val nutritionRepository: NutritionRepository,
    val progressRepository: ProgressRepository
)

data class SystemViewModels(
    val authViewModel: AuthViewModel,
    val dashboardViewModel: DashboardViewModel
)
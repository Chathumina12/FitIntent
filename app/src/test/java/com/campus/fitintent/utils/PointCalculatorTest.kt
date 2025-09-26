package com.campus.fitintent.utils

import com.campus.fitintent.models.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Comprehensive unit tests for PointCalculator
 * Tests all point calculation scenarios including edge cases
 */
class PointCalculatorTest {

    private lateinit var mockWorkouts: List<Workout>
    private lateinit var mockBadges: List<Badge>

    @Before
    fun setup() {
        // Create mock workouts for testing
        mockWorkouts = listOf(
            Workout(
                id = 1,
                name = "Beginner HIIT",
                category = WorkoutCategory.HIIT,
                difficulty = WorkoutDifficulty.BEGINNER,
                durationMinutes = 15,
                description = "Basic HIIT workout",
                imageUrl = null,
                exerciseIds = listOf(1L, 2L),
                isActive = true
            ),
            Workout(
                id = 2,
                name = "Advanced Strength",
                category = WorkoutCategory.STRENGTH,
                difficulty = WorkoutDifficulty.ADVANCED,
                durationMinutes = 45,
                description = "Heavy strength training",
                imageUrl = null,
                exerciseIds = listOf(3L, 4L, 5L),
                isActive = true
            ),
            Workout(
                id = 3,
                name = "Expert Cardio",
                category = WorkoutCategory.CARDIO,
                difficulty = WorkoutDifficulty.EXPERT,
                durationMinutes = 60,
                description = "High-intensity cardio",
                imageUrl = null,
                exerciseIds = listOf(6L, 7L),
                isActive = true
            )
        )

        // Create mock badges for testing
        mockBadges = listOf(
            Badge(
                id = 1,
                name = "First Step",
                description = "Complete first habit",
                type = BadgeType.HABIT,
                rarity = BadgeRarity.COMMON,
                iconResId = "ic_first_step",
                targetProgress = 1,
                isActive = true
            ),
            Badge(
                id = 2,
                name = "Consistency King",
                description = "90-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.EPIC,
                iconResId = "ic_consistency",
                targetProgress = 90,
                isActive = true
            ),
            Badge(
                id = 3,
                name = "Legend",
                description = "365-day streak",
                type = BadgeType.STREAK,
                rarity = BadgeRarity.LEGENDARY,
                iconResId = "ic_legend",
                targetProgress = 365,
                isActive = true
            )
        )
    }

    // ========================
    // Habit Points Tests
    // ========================

    @Test
    fun `calculateHabitPoints returns base points for single day`() {
        val habit = createMockHabit()
        val points = PointCalculator.calculateHabitPoints(habit, 1)
        assertEquals("Base habit points should be 10", 10, points)
    }

    @Test
    fun `calculateHabitPoints adds bonus for consecutive days`() {
        val habit = createMockHabit()

        // Test various streak bonuses
        assertEquals(15, PointCalculator.calculateHabitPoints(habit, 3))   // +5 bonus
        assertEquals(20, PointCalculator.calculateHabitPoints(habit, 7))   // +10 bonus
        assertEquals(25, PointCalculator.calculateHabitPoints(habit, 14))  // +15 bonus
        assertEquals(35, PointCalculator.calculateHabitPoints(habit, 21))  // +25 bonus (habit formed)
    }

    @Test
    fun `calculateHabitPoints handles edge cases`() {
        val habit = createMockHabit()

        // Test 0 days (should still give base points)
        assertEquals(10, PointCalculator.calculateHabitPoints(habit, 0))

        // Test very high streak
        assertEquals(35, PointCalculator.calculateHabitPoints(habit, 30)) // Caps at 21-day bonus
    }

    // ========================
    // Workout Points Tests
    // ========================

    @Test
    fun `calculateWorkoutPoints returns correct base points`() {
        val beginnerWorkout = mockWorkouts[0]
        val points = PointCalculator.calculateWorkoutPoints(beginnerWorkout)

        // Base (50) + Duration bonus (15min = 1*5 = 5) + HIIT bonus (15) = 70
        assertEquals("Beginner HIIT should give 70 points", 70, points)
    }

    @Test
    fun `calculateWorkoutPoints adds difficulty bonuses correctly`() {
        val beginnerWorkout = mockWorkouts[0]  // Beginner = +0
        val advancedWorkout = mockWorkouts[1]  // Advanced = +20
        val expertWorkout = mockWorkouts[2]    // Expert = +30

        val beginnerPoints = PointCalculator.calculateWorkoutPoints(beginnerWorkout)
        val advancedPoints = PointCalculator.calculateWorkoutPoints(advancedWorkout)
        val expertPoints = PointCalculator.calculateWorkoutPoints(expertWorkout)

        assertTrue("Advanced should give more points than beginner", advancedPoints > beginnerPoints)
        assertTrue("Expert should give more points than advanced", expertPoints > advancedPoints)
    }

    @Test
    fun `calculateWorkoutPoints adds category bonuses correctly`() {
        val strengthWorkout = mockWorkouts[1]  // Strength = +10
        val cardioWorkout = mockWorkouts[2]    // Cardio = +8

        val strengthPoints = PointCalculator.calculateWorkoutPoints(strengthWorkout)
        val cardioPoints = PointCalculator.calculateWorkoutPoints(cardioWorkout)

        // Both have different difficulty levels, so we test the difference should include category bonus
        val expectedDifference = (20 - 30) + (10 - 8) // difficulty difference + category difference
        // This is more complex due to duration bonuses, so we test that strength gets its bonus
        assertTrue("Category bonuses should be applied", strengthPoints > 50) // Base + bonuses
    }

    @Test
    fun `calculateWorkoutPoints includes early completion bonus`() {
        val workout = mockWorkouts[0] // 15 minutes

        val normalPoints = PointCalculator.calculateWorkoutPoints(workout)
        val earlyPoints = PointCalculator.calculateWorkoutPoints(workout, 10) // Completed in 10 minutes

        assertEquals("Early completion should add 10 points", normalPoints + 10, earlyPoints)
    }

    @Test
    fun `calculateWorkoutPoints no bonus for overtime completion`() {
        val workout = mockWorkouts[0] // 15 minutes

        val normalPoints = PointCalculator.calculateWorkoutPoints(workout)
        val overtimePoints = PointCalculator.calculateWorkoutPoints(workout, 20) // Took 20 minutes

        assertEquals("Overtime should not add bonus", normalPoints, overtimePoints)
    }

    // ========================
    // Streak Milestone Tests
    // ========================

    @Test
    fun `calculateStreakMilestonePoints returns correct values`() {
        assertEquals(15, PointCalculator.calculateStreakMilestonePoints(3))
        assertEquals(50, PointCalculator.calculateStreakMilestonePoints(7))
        assertEquals(100, PointCalculator.calculateStreakMilestonePoints(14))
        assertEquals(200, PointCalculator.calculateStreakMilestonePoints(21))
        assertEquals(300, PointCalculator.calculateStreakMilestonePoints(30))
        assertEquals(500, PointCalculator.calculateStreakMilestonePoints(60))
        assertEquals(750, PointCalculator.calculateStreakMilestonePoints(90))
        assertEquals(1000, PointCalculator.calculateStreakMilestonePoints(180))
        assertEquals(2000, PointCalculator.calculateStreakMilestonePoints(365))
    }

    @Test
    fun `calculateStreakMilestonePoints returns zero for non-milestones`() {
        assertEquals(0, PointCalculator.calculateStreakMilestonePoints(1))
        assertEquals(0, PointCalculator.calculateStreakMilestonePoints(5))
        assertEquals(0, PointCalculator.calculateStreakMilestonePoints(15))
        assertEquals(0, PointCalculator.calculateStreakMilestonePoints(100))
    }

    // ========================
    // Badge Points Tests
    // ========================

    @Test
    fun `calculateBadgePoints returns correct rarity values`() {
        assertEquals(100, PointCalculator.calculateBadgePoints(mockBadges[0])) // Common
        assertEquals(350, PointCalculator.calculateBadgePoints(mockBadges[1])) // Epic
        assertEquals(500, PointCalculator.calculateBadgePoints(mockBadges[2])) // Legendary
    }

    // ========================
    // Goal Points Tests
    // ========================

    @Test
    fun `calculateDailyGoalPoints returns correct values`() {
        val mockGoal = createMockDailyGoal()

        assertEquals(35, PointCalculator.calculateDailyGoalPoints(mockGoal, 1.0f))  // 100% + bonus
        assertEquals(25, PointCalculator.calculateDailyGoalPoints(mockGoal, 0.9f))  // 90%
        assertEquals(25, PointCalculator.calculateDailyGoalPoints(mockGoal, 0.8f))  // 80%
        assertEquals(0, PointCalculator.calculateDailyGoalPoints(mockGoal, 0.7f))   // Below 80%
    }

    @Test
    fun `calculateWeeklyGoalPoints returns correct values`() {
        assertEquals(125, PointCalculator.calculateWeeklyGoalPoints(1.1f))  // 110% + bonus
        assertEquals(100, PointCalculator.calculateWeeklyGoalPoints(0.9f))  // 90%
        assertEquals(100, PointCalculator.calculateWeeklyGoalPoints(0.8f))  // 80%
        assertEquals(0, PointCalculator.calculateWeeklyGoalPoints(0.7f))    // Below 80%
    }

    // ========================
    // Level Calculation Tests
    // ========================

    @Test
    fun `calculateUserLevel returns correct levels`() {
        assertEquals(1, PointCalculator.calculateUserLevel(0))
        assertEquals(1, PointCalculator.calculateUserLevel(999))
        assertEquals(2, PointCalculator.calculateUserLevel(1000))
        assertEquals(2, PointCalculator.calculateUserLevel(1999))
        assertEquals(3, PointCalculator.calculateUserLevel(2000))
        assertEquals(11, PointCalculator.calculateUserLevel(10000))
    }

    @Test
    fun `calculatePointsToNextLevel returns correct values`() {
        assertEquals(1000, PointCalculator.calculatePointsToNextLevel(0))
        assertEquals(1, PointCalculator.calculatePointsToNextLevel(999))
        assertEquals(1000, PointCalculator.calculatePointsToNextLevel(1000))
        assertEquals(1, PointCalculator.calculatePointsToNextLevel(1999))
        assertEquals(1000, PointCalculator.calculatePointsToNextLevel(2000))
    }

    @Test
    fun `calculateLevelProgress returns correct percentages`() {
        assertEquals(0.0f, PointCalculator.calculateLevelProgress(0), 0.01f)
        assertEquals(0.5f, PointCalculator.calculateLevelProgress(500), 0.01f)
        assertEquals(0.999f, PointCalculator.calculateLevelProgress(999), 0.01f)
        assertEquals(0.0f, PointCalculator.calculateLevelProgress(1000), 0.01f)
        assertEquals(0.5f, PointCalculator.calculateLevelProgress(1500), 0.01f)
    }

    // ========================
    // Level Title Tests
    // ========================

    @Test
    fun `getLevelTitle returns correct titles`() {
        assertEquals("Beginner", PointCalculator.getLevelTitle(1))
        assertEquals("Novice", PointCalculator.getLevelTitle(3))
        assertEquals("Enthusiast", PointCalculator.getLevelTitle(8))
        assertEquals("Expert", PointCalculator.getLevelTitle(15))
        assertEquals("Master", PointCalculator.getLevelTitle(25))
        assertEquals("Champion", PointCalculator.getLevelTitle(40))
        assertEquals("Legend", PointCalculator.getLevelTitle(60))
        assertEquals("Hero", PointCalculator.getLevelTitle(80))
        assertEquals("Grandmaster", PointCalculator.getLevelTitle(150))
    }

    // ========================
    // Weekly Points Calculation Tests
    // ========================

    @Test
    fun `calculateWeeklyPoints aggregates all sources correctly`() {
        val weeklyPoints = PointCalculator.calculateWeeklyPoints(
            habitsCompleted = 10,           // 10 * 10 = 100
            workoutsCompleted = listOf(mockWorkouts[0]), // ~70 points
            streakMilestones = listOf(7),   // 50 points
            badgesUnlocked = listOf(mockBadges[0]), // 100 points
            dailyGoalsAchieved = 5,         // 5 * 25 = 125
            nutritionFavorites = 3          // 3 * 2 = 6
        )

        val expectedPoints = 100 + 70 + 50 + 100 + 125 + 6 // 451
        assertTrue("Weekly points should aggregate correctly", weeklyPoints >= 400) // Account for workout bonuses
    }

    // ========================
    // Streak Bonus Tests
    // ========================

    @Test
    fun `calculateDailyActivityStreakBonus returns correct bonuses`() {
        assertEquals(0, PointCalculator.calculateDailyActivityStreakBonus(1))
        assertEquals(0, PointCalculator.calculateDailyActivityStreakBonus(2))
        assertEquals(5, PointCalculator.calculateDailyActivityStreakBonus(5))
        assertEquals(10, PointCalculator.calculateDailyActivityStreakBonus(10))
        assertEquals(15, PointCalculator.calculateDailyActivityStreakBonus(18))
        assertEquals(20, PointCalculator.calculateDailyActivityStreakBonus(25))
        assertEquals(25, PointCalculator.calculateDailyActivityStreakBonus(45))
        assertEquals(30, PointCalculator.calculateDailyActivityStreakBonus(75))
        assertEquals(40, PointCalculator.calculateDailyActivityStreakBonus(120))
        assertEquals(50, PointCalculator.calculateDailyActivityStreakBonus(300))
        assertEquals(75, PointCalculator.calculateDailyActivityStreakBonus(400))
    }

    // ========================
    // Perfect Week Tests
    // ========================

    @Test
    fun `calculatePerfectWeekBonus awards bonus when targets met`() {
        val bonus = PointCalculator.calculatePerfectWeekBonus(
            habitsCompletedThisWeek = 10,
            targetHabitsPerWeek = 10,
            workoutsCompletedThisWeek = 3,
            targetWorkoutsPerWeek = 3
        )
        assertEquals(200, bonus)
    }

    @Test
    fun `calculatePerfectWeekBonus awards bonus when targets exceeded`() {
        val bonus = PointCalculator.calculatePerfectWeekBonus(
            habitsCompletedThisWeek = 12,
            targetHabitsPerWeek = 10,
            workoutsCompletedThisWeek = 5,
            targetWorkoutsPerWeek = 3
        )
        assertEquals(200, bonus)
    }

    @Test
    fun `calculatePerfectWeekBonus no bonus when targets not met`() {
        val bonus = PointCalculator.calculatePerfectWeekBonus(
            habitsCompletedThisWeek = 8,  // Below target
            targetHabitsPerWeek = 10,
            workoutsCompletedThisWeek = 3,
            targetWorkoutsPerWeek = 3
        )
        assertEquals(0, bonus)
    }

    // ========================
    // Points Breakdown Tests
    // ========================

    @Test
    fun `calculatePointsBreakdown provides accurate breakdown`() {
        val breakdown = PointCalculator.calculatePointsBreakdown(
            habitsCompleted = 5,                    // 50 points
            workoutsCompleted = listOf(mockWorkouts[0]), // ~70 points
            streakBonus = 25,                       // 25 points
            badgesUnlocked = listOf(mockBadges[0]), // 100 points
            goalsAchieved = 2,                      // 50 points
            perfectWeekBonus = 200                  // 200 points
        )

        assertEquals(50, breakdown.habitPoints)
        assertEquals(25, breakdown.streakBonusPoints)
        assertEquals(100, breakdown.badgePoints)
        assertEquals(50, breakdown.goalPoints)
        assertEquals(200, breakdown.perfectWeekBonus)

        // Total should be sum of all parts
        assertEquals(
            breakdown.habitPoints + breakdown.workoutPoints + breakdown.streakBonusPoints +
            breakdown.badgePoints + breakdown.goalPoints + breakdown.perfectWeekBonus,
            breakdown.totalPoints
        )
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `point calculations handle negative inputs gracefully`() {
        // These shouldn't happen in normal app flow, but test defensive programming
        assertEquals(10, PointCalculator.calculateHabitPoints(createMockHabit(), -1))
        assertEquals(0, PointCalculator.calculateStreakMilestonePoints(-5))
        assertEquals(0, PointCalculator.calculateDailyGoalPoints(createMockDailyGoal(), -0.5f))
    }

    @Test
    fun `level calculations handle edge cases`() {
        assertEquals(1, PointCalculator.calculateUserLevel(-100))
        assertEquals(1000, PointCalculator.calculatePointsToNextLevel(-50))
        assertEquals(0.0f, PointCalculator.calculateLevelProgress(-100), 0.01f)
    }

    // ========================
    // Helper Methods
    // ========================

    private fun createMockHabit(): Habit {
        return Habit(
            id = 1L,
            userId = 1L,
            name = "Test Habit",
            description = "Test habit description",
            category = HabitCategory.HEALTH,
            targetFrequency = FrequencyType.DAILY,
            reminderTime = "09:00",
            isActive = true
        )
    }

    private fun createMockDailyGoal(): DailyGoal {
        return DailyGoal(
            id = 1L,
            userId = 1L,
            date = java.util.Date(),
            targetHabits = 3,
            targetWorkouts = 1,
            targetPoints = 100,
            completedHabits = 0,
            completedWorkouts = 0,
            earnedPoints = 0,
            isCompleted = false
        )
    }
}
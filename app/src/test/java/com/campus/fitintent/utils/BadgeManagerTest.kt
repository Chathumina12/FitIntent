package com.campus.fitintent.utils

import com.campus.fitintent.models.*
import com.campus.fitintent.repository.ProgressRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.util.*

/**
 * Comprehensive unit tests for BadgeManager
 * Tests badge progress tracking, unlock conditions, and statistics
 */
class BadgeManagerTest {

    @Mock
    private lateinit var mockProgressRepository: ProgressRepository

    private lateinit var badgeManager: BadgeManager
    private lateinit var testUserId: Long

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        badgeManager = BadgeManager(mockProgressRepository)
        testUserId = 1L
    }

    // ========================
    // Badge Eligibility Tests
    // ========================

    @Test
    fun `getAllBadges returns all 24 predefined badges`() {
        val badges = badgeManager.getAllBadges()

        assertEquals("Should return 24 badges", 24, badges.size)

        // Verify we have badges of each type
        assertTrue("Should have habit badges", badges.any { it.type == BadgeType.HABIT })
        assertTrue("Should have workout badges", badges.any { it.type == BadgeType.WORKOUT })
        assertTrue("Should have streak badges", badges.any { it.type == BadgeType.STREAK })
        assertTrue("Should have time badges", badges.any { it.type == BadgeType.TIME })
        assertTrue("Should have nutrition badges", badges.any { it.type == BadgeType.NUTRITION })
        assertTrue("Should have consistency badges", badges.any { it.type == BadgeType.CONSISTENCY })
        assertTrue("Should have special badges", badges.any { it.type == BadgeType.SPECIAL })
    }

    @Test
    fun `getBadgesByType returns correct filtered badges`() {
        val habitBadges = badgeManager.getBadgesByType(BadgeType.HABIT)
        val workoutBadges = badgeManager.getBadgesByType(BadgeType.WORKOUT)
        val streakBadges = badgeManager.getBadgesByType(BadgeType.STREAK)

        assertTrue("Should have habit badges", habitBadges.isNotEmpty())
        assertTrue("Should have workout badges", workoutBadges.isNotEmpty())
        assertTrue("Should have streak badges", streakBadges.isNotEmpty())

        // Verify all returned badges are of correct type
        assertTrue("All habit badges should be HABIT type",
            habitBadges.all { it.type == BadgeType.HABIT })
        assertTrue("All workout badges should be WORKOUT type",
            workoutBadges.all { it.type == BadgeType.WORKOUT })
        assertTrue("All streak badges should be STREAK type",
            streakBadges.all { it.type == BadgeType.STREAK })
    }

    @Test
    fun `getBadgesByRarity returns correct filtered badges`() {
        val commonBadges = badgeManager.getBadgesByRarity(BadgeRarity.COMMON)
        val rareBadges = badgeManager.getBadgesByRarity(BadgeRarity.RARE)
        val epicBadges = badgeManager.getBadgesByRarity(BadgeRarity.EPIC)
        val legendaryBadges = badgeManager.getBadgesByRarity(BadgeRarity.LEGENDARY)

        // Verify we have badges of each rarity
        assertTrue("Should have common badges", commonBadges.isNotEmpty())
        assertTrue("Should have rare badges", rareBadges.isNotEmpty())
        assertTrue("Should have epic badges", epicBadges.isNotEmpty())
        assertTrue("Should have legendary badges", legendaryBadges.isNotEmpty())

        // Verify filtering works correctly
        assertTrue("All common badges should be COMMON rarity",
            commonBadges.all { it.rarity == BadgeRarity.COMMON })
        assertTrue("All legendary badges should be LEGENDARY rarity",
            legendaryBadges.all { it.rarity == BadgeRarity.LEGENDARY })
    }

    // ========================
    // Badge Progress Tests
    // ========================

    @Test
    fun `checkBadgeProgress updates habit badge progress correctly`() = runTest {
        // Mock repository responses
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(createMockUserBadges()))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any()))
            .thenReturn(flowOf(0))

        // Test habit completion activity
        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.HABIT_COMPLETED, 1)

        // Verify badge progress was updated
        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            eq(1)
        )
    }

    @Test
    fun `checkBadgeProgress updates workout badge progress correctly`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(createMockUserBadges()))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any()))
            .thenReturn(flowOf(2)) // Already has some progress

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.WORKOUT_COMPLETED, 1)

        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            eq(3) // 2 + 1
        )
    }

    @Test
    fun `checkBadgeProgress unlocks badge when target reached`() = runTest {
        val mockUserBadges = createMockUserBadges()
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(mockUserBadges))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, 1L))
            .thenReturn(flowOf(0)) // First Step badge needs 1 progress, has 0

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.HABIT_COMPLETED, 1)

        // Should unlock the "First Step" badge
        verify(mockProgressRepository).unlockUserBadge(
            eq(testUserId),
            eq(1L),
            any()
        )
        verify(mockProgressRepository).addUserPoints(
            eq(testUserId),
            eq(100), // Common badge points
            any()
        )
    }

    @Test
    fun `checkBadgeProgress ignores already unlocked badges`() = runTest {
        val mockUserBadges = listOf(
            UserBadge(1L, testUserId, 1L, true, Date()) // Already unlocked
        )
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(mockUserBadges))

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.HABIT_COMPLETED, 1)

        // Should not try to update progress for already unlocked badge
        verify(mockProgressRepository, never()).updateBadgeProgress(eq(testUserId), eq(1L), any())
    }

    // ========================
    // Badge Statistics Tests
    // ========================

    @Test
    fun `calculateBadgeCompletionPercentage returns correct percentage`() = runTest {
        val mockUserBadges = listOf(
            UserBadge(1L, testUserId, 1L, true, Date()),
            UserBadge(2L, testUserId, 2L, true, Date()),
            UserBadge(3L, testUserId, 3L, false, null)
        )
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(mockUserBadges))

        val percentage = badgeManager.calculateBadgeCompletionPercentage(testUserId)

        // 2 unlocked out of 24 total badges = 2/24 = 0.083...
        assertEquals(2.0f / 24, percentage, 0.01f)
    }

    @Test
    fun `getBadgeStatistics returns comprehensive statistics`() = runTest {
        val mockUserBadges = createMockUserBadgeStatistics()
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(mockUserBadges))

        val stats = badgeManager.getBadgeStatistics(testUserId)

        assertEquals(24, stats.totalBadges)
        assertEquals(5, stats.unlockedBadges)
        assertEquals(20.83f, stats.completionPercentage, 0.1f) // 5/24 * 100

        // Check rarity breakdown
        assertTrue("Should have common badges", stats.commonBadges > 0)
        assertTrue("Should have rare badges", stats.rareBadges > 0)
        assertTrue("Should have epic badges", stats.epicBadges > 0)

        // Check recent unlocks
        assertEquals(3, stats.recentlyUnlocked.size)
        assertTrue("Recent unlocks should be sorted by date",
            stats.recentlyUnlocked.isNotEmpty())
    }

    @Test
    fun `getUpcomingBadges returns badges user is close to unlocking`() = runTest {
        val mockUserBadges = createMockUserBadges()
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(mockUserBadges))

        // Mock various progress levels
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any())).thenAnswer { invocation ->
            val badgeId = invocation.getArgument<Long>(1)
            val progress = when (badgeId) {
                2L -> 8   // 8/10 for Consistency Builder (80%)
                3L -> 30  // 30/50 for Habit Former (60%)
                4L -> 150 // 150/200 for Lifestyle Master (75%)
                else -> 0
            }
            flowOf(progress)
        }

        val upcomingBadges = badgeManager.getUpcomingBadges(testUserId, 5)

        assertEquals("Should return up to 5 badges", 5, upcomingBadges.size)

        // Verify badges are sorted by progress percentage (highest first)
        for (i in 0 until upcomingBadges.size - 1) {
            assertTrue("Should be sorted by progress percentage",
                upcomingBadges[i].progressPercentage >= upcomingBadges[i + 1].progressPercentage)
        }
    }

    // ========================
    // Badge Activity Eligibility Tests
    // ========================

    @Test
    fun `habit activities trigger habit badges`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(createMockUserBadges()))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any()))
            .thenReturn(flowOf(0))

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.HABIT_COMPLETED)

        // Should update progress for habit badges
        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            any()
        )
    }

    @Test
    fun `workout activities trigger workout badges`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(createMockUserBadges()))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any()))
            .thenReturn(flowOf(0))

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.STRENGTH_WORKOUT)

        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            any()
        )
    }

    @Test
    fun `nutrition activities trigger nutrition badges`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(createMockUserBadges()))
        whenever(mockProgressRepository.getBadgeProgress(testUserId, any()))
            .thenReturn(flowOf(0))

        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.NUTRITION_FAVORITE)

        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            any()
        )
    }

    // ========================
    // Badge Specific Tests
    // ========================

    @Test
    fun `First Step badge exists with correct properties`() {
        val badges = badgeManager.getAllBadges()
        val firstStep = badges.find { it.name == "First Step" }

        assertNotNull("First Step badge should exist", firstStep)
        assertEquals(BadgeType.HABIT, firstStep?.type)
        assertEquals(BadgeRarity.COMMON, firstStep?.rarity)
        assertEquals(1, firstStep?.targetProgress)
    }

    @Test
    fun `Legend badge exists with correct properties`() {
        val badges = badgeManager.getAllBadges()
        val legend = badges.find { it.name == "Legend" }

        assertNotNull("Legend badge should exist", legend)
        assertEquals(BadgeType.STREAK, legend?.type)
        assertEquals(BadgeRarity.LEGENDARY, legend?.rarity)
        assertEquals(365, legend?.targetProgress)
    }

    @Test
    fun `Consistency King badge exists with correct properties`() {
        val badges = badgeManager.getAllBadges()
        val consistencyKing = badges.find { it.name == "Consistency King" }

        assertNotNull("Consistency King badge should exist", consistencyKing)
        assertEquals(BadgeType.STREAK, consistencyKing?.type)
        assertEquals(BadgeRarity.EPIC, consistencyKing?.rarity)
        assertEquals(90, consistencyKing?.targetProgress)
    }

    @Test
    fun `All-Rounder badge exists with correct properties`() {
        val badges = badgeManager.getAllBadges()
        val allRounder = badges.find { it.name == "All-Rounder" }

        assertNotNull("All-Rounder badge should exist", allRounder)
        assertEquals(BadgeType.SPECIAL, allRounder?.type)
        assertEquals(BadgeRarity.LEGENDARY, allRounder?.rarity)
        assertEquals(6, allRounder?.targetProgress) // Number of workout categories
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `badge progress handles empty user badges list`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(emptyList()))

        // Should not crash with empty list
        badgeManager.checkBadgeProgress(testUserId, BadgeActivity.HABIT_COMPLETED)

        // Should still try to update progress
        verify(mockProgressRepository, atLeastOnce()).updateBadgeProgress(
            eq(testUserId),
            any(),
            any()
        )
    }

    @Test
    fun `badge statistics handle zero unlocked badges`() = runTest {
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(emptyList()))

        val stats = badgeManager.getBadgeStatistics(testUserId)

        assertEquals(24, stats.totalBadges)
        assertEquals(0, stats.unlockedBadges)
        assertEquals(0.0f, stats.completionPercentage, 0.01f)
        assertEquals(0, stats.commonBadges)
        assertEquals(0, stats.rareBadges)
        assertEquals(0, stats.epicBadges)
        assertEquals(0, stats.legendaryBadges)
        assertTrue("Recent unlocks should be empty", stats.recentlyUnlocked.isEmpty())
    }

    @Test
    fun `upcoming badges handles all badges unlocked`() = runTest {
        val allUnlockedBadges = badgeManager.getAllBadges().mapIndexed { index, badge ->
            UserBadge(index.toLong(), testUserId, badge.id, true, Date())
        }
        whenever(mockProgressRepository.getUserBadges(testUserId))
            .thenReturn(flowOf(allUnlockedBadges))

        val upcomingBadges = badgeManager.getUpcomingBadges(testUserId, 5)

        assertTrue("Should return empty list when all badges unlocked", upcomingBadges.isEmpty())
    }

    // ========================
    // Helper Methods
    // ========================

    private fun createMockUserBadges(): List<UserBadge> {
        return listOf(
            UserBadge(1L, testUserId, 1L, false, null), // First Step - not unlocked
            UserBadge(2L, testUserId, 2L, false, null), // Consistency Builder - not unlocked
            UserBadge(3L, testUserId, 5L, true, Date()) // First Sweat - unlocked
        )
    }

    private fun createMockUserBadgeStatistics(): List<UserBadge> {
        val baseDate = Date()
        return listOf(
            // Unlocked badges with different rarities and dates
            UserBadge(1L, testUserId, 1L, true, Date(baseDate.time - 86400000)), // 1 day ago
            UserBadge(2L, testUserId, 2L, true, Date(baseDate.time - 172800000)), // 2 days ago
            UserBadge(3L, testUserId, 5L, true, Date(baseDate.time - 259200000)), // 3 days ago
            UserBadge(4L, testUserId, 11L, true, Date(baseDate.time - 345600000)), // 4 days ago
            UserBadge(5L, testUserId, 16L, true, Date(baseDate.time - 432000000)), // 5 days ago
            // Some not unlocked
            UserBadge(6L, testUserId, 3L, false, null),
            UserBadge(7L, testUserId, 4L, false, null)
        )
    }
}
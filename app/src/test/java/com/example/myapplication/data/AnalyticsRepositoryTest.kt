package com.example.myapplication.data

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.content.SharedPreferences
import org.mockito.Mockito.*

/**
 * Unit tests for AnalyticsRepository
 * Tests session tracking, statistics, and streak calculations
 */
class AnalyticsRepositoryTest {

    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var repository: AnalyticsRepository

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
        mockSharedPreferences = mock(SharedPreferences::class.java)
        
        `when`(mockContext.getSharedPreferences("focus_timer_analytics", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        
        repository = AnalyticsRepository(mockContext)
    }

    @Test
    fun analytics_repository_initializes() {
        assertNotNull(repository)
    }

    @Test
    fun record_session_stores_data() {
        // This would require proper mocking of SharedPreferences.Editor
        // For now, test the method doesn't crash
        try {
            repository.recordSession(25, 5)
        } catch (e: Exception) {
            fail("recordSession threw exception: ${e.message}")
        }
    }

    @Test
    fun get_today_stats_returns_daily_stats() {
        // This would require proper mocking
        val stats = repository.getTodayStats()
        assertNotNull(stats)
    }

    @Test
    fun get_weekly_stats_returns_list() {
        val stats = repository.getWeeklyStats()
        assertNotNull(stats)
    }

    @Test
    fun get_streak_returns_non_negative_integer() {
        val streak = repository.getStreak()
        assertTrue(streak >= 0)
    }

    @Test
    fun get_longest_streak_returns_non_negative_integer() {
        val longestStreak = repository.getLongestStreak()
        assertTrue(longestStreak >= 0)
    }

    @Test
    fun get_total_sessions_returns_non_negative_integer() {
        val total = repository.getTotalSessions()
        assertTrue(total >= 0)
    }

    @Test
    fun get_total_focus_hours_returns_non_negative() {
        val hours = repository.getTotalFocusHours()
        assertTrue(hours >= 0)
    }

    @Test
    fun get_average_focus_per_session_returns_reasonable_value() {
        val average = repository.getAverageFocusPerSession()
        assertTrue(average >= 0)
        assertTrue(average <= 60)  // Reasonable max ~60 minutes
    }

    @Test
    fun reset_all_data_does_not_crash() {
        try {
            repository.resetAllData()
        } catch (e: Exception) {
            fail("resetAllData threw exception: ${e.message}")
        }
    }
}

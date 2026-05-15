package com.example.myapplication.data

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.content.SharedPreferences
import org.mockito.Mockito.*

/**
 * Unit tests for SessionRepository
 * Tests session tracking and data persistence
 */
class SessionRepositoryTest {

    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var repository: SessionRepository

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
        mockSharedPreferences = mock(SharedPreferences::class.java)

        `when`(mockContext.getSharedPreferences("focus_timer_prefs", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)

        repository = SessionRepository(mockContext)
    }

    @Test
    fun session_repository_initializes() {
        assertNotNull(repository)
    }

    @Test
    fun get_sessions_completed_returns_non_negative() {
        val sessions = repository.getSessionsCompleted()
        assertTrue(sessions >= 0)
    }

    @Test
    fun get_total_focus_minutes_returns_non_negative() {
        val minutes = repository.getTotalFocusMinutes()
        assertTrue(minutes >= 0)
    }

    @Test
    fun get_today_date_returns_string() {
        val date = repository.getTodayDate()
        assertNotNull(date)
        assertTrue(date is String)
    }

    @Test
    fun increment_sessions_does_not_crash() {
        try {
            repository.incrementSessionsCompleted()
        } catch (e: Exception) {
            fail("incrementSessionsCompleted threw exception: ${e.message}")
        }
    }

    @Test
    fun increment_focus_minutes_does_not_crash() {
        try {
            repository.incrementTotalFocusMinutes(25)
        } catch (e: Exception) {
            fail("incrementTotalFocusMinutes threw exception: ${e.message}")
        }
    }

    @Test
    fun save_today_date_does_not_crash() {
        try {
            repository.saveTodayDate("2026-05-15")
        } catch (e: Exception) {
            fail("saveTodayDate threw exception: ${e.message}")
        }
    }

    @Test
    fun reset_daily_stats_does_not_crash() {
        try {
            repository.resetDailyStats()
        } catch (e: Exception) {
            fail("resetDailyStats threw exception: ${e.message}")
        }
    }
}


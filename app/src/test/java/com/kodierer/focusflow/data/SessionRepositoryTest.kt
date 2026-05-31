package com.kodierer.focusflow.data

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.content.SharedPreferences
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Unit tests for SessionRepository
 * Tests session tracking and data persistence
 */
class SessionRepositoryTest {

    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor
    private lateinit var repository: SessionRepository

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
        mockSharedPreferences = mock(SharedPreferences::class.java)
        mockEditor = mock(SharedPreferences.Editor::class.java)

        `when`(mockContext.getSharedPreferences("focus_timer_prefs", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor)
        `when`(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor)
        `when`(mockEditor.apply()).then { /* no-op for test */ }

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
    fun get_today_date_returns_empty_string_by_default() {
        val date = repository.getTodayDate()
        assertEquals("", date)
    }

    @Test
    fun get_today_date_returns_previously_saved_value() {
        whenever(mockSharedPreferences.getString("today_date", "")).thenReturn("2026-05-10")

        val date = repository.getTodayDate()

        assertEquals("2026-05-10", date)
    }

    @Test
    fun increment_sessions_calls_editor_correctly() {
        whenever(mockSharedPreferences.getInt("sessions_completed", 0)).thenReturn(3)

        repository.incrementSessionsCompleted()

        verify(mockEditor).putInt("sessions_completed", 4)
        verify(mockEditor).apply()
    }

    @Test
    fun increment_focus_minutes_calls_editor_correctly() {
        whenever(mockSharedPreferences.getInt("total_focus_minutes", 0)).thenReturn(40)

        repository.incrementTotalFocusMinutes(25)

        verify(mockEditor).putInt("total_focus_minutes", 65)
        verify(mockEditor).apply()
    }

    @Test
    fun save_today_date_stores_value() {
        repository.saveTodayDate("2026-05-15")

        verify(mockEditor).putString("today_date", "2026-05-15")
        verify(mockEditor).apply()
    }

    @Test
    fun reset_daily_stats_clears_all_keys() {
        repository.resetDailyStats()

        verify(mockEditor).putInt("sessions_completed", 0)
        verify(mockEditor).putInt("total_focus_minutes", 0)
        verify(mockEditor).putString("today_date", "")
        verify(mockEditor).apply()
    }
}

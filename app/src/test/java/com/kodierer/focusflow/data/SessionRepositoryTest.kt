package com.kodierer.focusflow.data

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.content.SharedPreferences
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
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
        `when`(mockEditor.putBoolean(anyString(), anyBoolean())).thenReturn(mockEditor)
        `when`(mockEditor.putLong(anyString(), anyLong())).thenReturn(mockEditor)
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
    fun save_timer_state_stores_all_values() {
        repository.saveTimerState(
            PersistedTimerState(
                workMinutes = 30,
                breakMinutes = 7,
                timeLeft = 1234,
                isRunning = true,
                isWorkSession = false,
                savedAtMillis = 42L
            )
        )

        verify(mockEditor).putInt("timer_work_minutes", 30)
        verify(mockEditor).putInt("timer_break_minutes", 7)
        verify(mockEditor).putInt("timer_time_left", 1234)
        verify(mockEditor).putBoolean("timer_is_running", true)
        verify(mockEditor).putBoolean("timer_is_work_session", false)
        verify(mockEditor).putLong("timer_saved_at", 42L)
        verify(mockEditor).apply()
    }

    @Test
    fun get_timer_state_returns_null_when_not_saved() {
        whenever(mockSharedPreferences.contains("timer_time_left")).thenReturn(false)

        assertNull(repository.getTimerState())
    }

    @Test
    fun get_timer_state_returns_saved_values() {
        whenever(mockSharedPreferences.contains("timer_time_left")).thenReturn(true)
        whenever(mockSharedPreferences.getInt("timer_work_minutes", 25)).thenReturn(35)
        whenever(mockSharedPreferences.getInt("timer_break_minutes", 5)).thenReturn(9)
        whenever(mockSharedPreferences.getInt("timer_time_left", 25 * 60)).thenReturn(777)
        whenever(mockSharedPreferences.getBoolean("timer_is_running", false)).thenReturn(true)
        whenever(mockSharedPreferences.getBoolean("timer_is_work_session", true)).thenReturn(false)
        whenever(mockSharedPreferences.getLong("timer_saved_at", 0L)).thenReturn(99L)

        val timerState = repository.getTimerState()

        assertNotNull(timerState)
        assertEquals(35, timerState?.workMinutes)
        assertEquals(9, timerState?.breakMinutes)
        assertEquals(777, timerState?.timeLeft)
        assertTrue(timerState?.isRunning == true)
        assertTrue(timerState?.isWorkSession == false)
        assertEquals(99L, timerState?.savedAtMillis)
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

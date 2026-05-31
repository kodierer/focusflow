package com.kodierer.focusflow.data

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.content.SharedPreferences
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

/**
 * Unit tests for AnalyticsRepository
 * Tests session tracking, statistics, and streak calculations
 */
class AnalyticsRepositoryTest {

    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor
    private lateinit var repository: AnalyticsRepository

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
        mockSharedPreferences = mock(SharedPreferences::class.java)
        mockEditor = mock(SharedPreferences.Editor::class.java)

        `when`(mockContext.getSharedPreferences("focus_timer_analytics", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor)
        `when`(mockEditor.clear()).thenReturn(mockEditor)
        `when`(mockEditor.apply()).then { /* no-op */ }

        repository = AnalyticsRepository(mockContext)
    }

    @Test
    fun analytics_repository_initializes() {
        assertNotNull(repository)
    }

    @Test
    fun record_session_writes_correct_daily_keys() {
        repository.recordSession(25, 5)

        val keyCaptor = ArgumentCaptor.forClass(String::class.java)
        val valueCaptor = ArgumentCaptor.forClass(Int::class.java)

        // recordSession writes 3 different keys for the current day
        verify(mockEditor, times(3)).putInt(keyCaptor.capture(), valueCaptor.capture())
        verify(mockEditor).apply()

        val capturedKeys = keyCaptor.allValues

        assertTrue(capturedKeys.any { it.startsWith("session_") })
        assertTrue(capturedKeys.any { it.startsWith("focus_") })
        assertTrue(capturedKeys.any { it.startsWith("break_") })
    }

    @Test
    fun get_today_stats_returns_valid_daily_stats_object() {
        val stats = repository.getTodayStats()

        assertNotNull(stats)
        assertNotNull(stats.date)
        assertTrue(stats.sessionsCompleted >= 0)
        assertTrue(stats.focusMinutes >= 0)
    }

    @Test
    fun get_weekly_stats_returns_list_of_7_days() {
        val stats = repository.getWeeklyStats()

        assertEquals(7, stats.size)
    }

    @Test
    fun reset_all_data_calls_clear() {
        repository.resetAllData()

        verify(mockEditor).clear()
        verify(mockEditor).apply()
    }
}

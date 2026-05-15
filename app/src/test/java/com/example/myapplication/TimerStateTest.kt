package com.example.myapplication

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for TimerState data class
 * Tests state initialization and properties
 */
class TimerStateTest {

    private lateinit var timerState: TimerState

    @Before
    fun setup() {
        timerState = TimerState()
    }

    @Test
    fun timer_state_has_correct_default_values() {
        assertEquals(25, timerState.workMinutes)
        assertEquals(5, timerState.breakMinutes)
        assertEquals(25 * 60, timerState.timeLeft)
        assertFalse(timerState.isRunning)
        assertTrue(timerState.isWorkSession)
        assertEquals(0, timerState.sessionsCompleted)
        assertEquals(0, timerState.totalFocusMinutes)
    }

    @Test
    fun timer_state_can_be_copied_with_modifications() {
        val modified = timerState.copy(
            workMinutes = 30,
            isRunning = true,
            sessionsCompleted = 5
        )

        assertEquals(30, modified.workMinutes)
        assertTrue(modified.isRunning)
        assertEquals(5, modified.sessionsCompleted)

        // Original should be unchanged
        assertEquals(25, timerState.workMinutes)
        assertFalse(timerState.isRunning)
        assertEquals(0, timerState.sessionsCompleted)
    }

    @Test
    fun timer_state_tracks_all_properties() {
        val state = TimerState(
            workMinutes = 30,
            breakMinutes = 10,
            timeLeft = 1500,
            isRunning = true,
            isWorkSession = false,
            sessionsCompleted = 5,
            totalFocusMinutes = 150
        )

        assertEquals(30, state.workMinutes)
        assertEquals(10, state.breakMinutes)
        assertEquals(1500, state.timeLeft)
        assertTrue(state.isRunning)
        assertFalse(state.isWorkSession)
        assertEquals(5, state.sessionsCompleted)
        assertEquals(150, state.totalFocusMinutes)
    }

    @Test
    fun timer_state_is_immutable_data_class() {
        val state1 = TimerState(sessionsCompleted = 3)
        val state2 = state1.copy(sessionsCompleted = 5)

        assertNotEquals(state1.sessionsCompleted, state2.sessionsCompleted)
        assertEquals(3, state1.sessionsCompleted)
        assertEquals(5, state2.sessionsCompleted)
    }
}


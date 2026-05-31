package com.kodierer.focusflow

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for TimerViewModel
 * Tests core timer functionality, state management, and session tracking
 */
class TimerViewModelTest {

    private lateinit var viewModel: TimerViewModel

    @Before
    fun setup() {
        viewModel = TimerViewModel()
    }

    @Test
    fun timer_initializes_with_correct_defaults() {
        assertEquals(25, viewModel.state.value.workMinutes)
        assertEquals(5, viewModel.state.value.breakMinutes)
        assertEquals(25 * 60, viewModel.state.value.timeLeft)
        assertFalse(viewModel.state.value.isRunning)
        assertTrue(viewModel.state.value.isWorkSession)
        assertEquals(0, viewModel.state.value.sessionsCompleted)
    }

    @Test
    fun timer_respects_initial_settings() {
        val state = viewModel.state.value
        assertEquals(25, state.workMinutes)
        assertEquals(5, state.breakMinutes)
        assertEquals(25 * 60, state.timeLeft)
    }

    @Test
    fun can_set_work_minutes() {
        viewModel.setWorkMinutes(20)
        assertEquals(20, viewModel.state.value.workMinutes)
        assertEquals(20 * 60, viewModel.state.value.timeLeft)
    }

    @Test
    fun can_set_break_minutes() {
        viewModel.setBreakMinutes(10)
        assertEquals(10, viewModel.state.value.breakMinutes)
    }

    @Test
    fun setWorkMinutes_updates_value_when_not_running() {
        viewModel.setWorkMinutes(45)
        assertEquals(45, viewModel.state.value.workMinutes)
    }

    @Test
    fun setBreakMinutes_updates_value_when_not_running() {
        viewModel.setBreakMinutes(10)
        assertEquals(10, viewModel.state.value.breakMinutes)
    }

    // Note: Tests that start the real timer (startTimer / pauseTimer) are difficult
    // without Robolectric because TimerViewModel uses Handler + Looper.getMainLooper().
    // Core timer behavior is covered in TimerViewModelPersistenceTest + manual testing.
}

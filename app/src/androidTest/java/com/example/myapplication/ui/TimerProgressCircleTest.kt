package com.example.myapplication.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myapplication.TimerState
import com.example.myapplication.ui.screens.TimerProgressCircle
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for the new beautiful animated TimerProgressCircle.
 * These tests verify the major visual improvement we added for attractiveness.
 */
class TimerProgressCircleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progress_circle_displays_correct_time_for_work_session() {
        val state = TimerState(
            workMinutes = 25,
            timeLeft = 25 * 60,
            isWorkSession = true,
            isRunning = false
        )

        composeTestRule.setContent {
            TimerProgressCircle(state = state)
        }

        // The large timer text should be visible
        composeTestRule
            .onNodeWithText("25:00")
            .assertIsDisplayed()

        // Should show FOKUS label for work session
        composeTestRule
            .onNodeWithText("FOKUS")
            .assertIsDisplayed()
    }

    @Test
    fun progress_circle_displays_pause_label_for_break_session() {
        val state = TimerState(
            breakMinutes = 5,
            timeLeft = 5 * 60,
            isWorkSession = false,
            isRunning = true
        )

        composeTestRule.setContent {
            TimerProgressCircle(state = state)
        }

        composeTestRule
            .onNodeWithText("05:00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("PAUSE")
            .assertIsDisplayed()
    }

    @Test
    fun progress_circle_shows_correct_time_when_partially_elapsed() {
        val state = TimerState(
            workMinutes = 25,
            timeLeft = 12 * 60 + 30,   // 12:30 remaining
            isWorkSession = true
        )

        composeTestRule.setContent {
            TimerProgressCircle(state = state)
        }

        composeTestRule
            .onNodeWithText("12:30")
            .assertIsDisplayed()
    }
}
package com.example.myapplication.utils

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.content.Context
import android.os.Vibrator
import org.mockito.Mockito.*

/**
 * Unit tests for HapticFeedback utility
 * Tests vibration feedback functionality
 */
class HapticFeedbackTest {

    private lateinit var mockContext: Context
    private lateinit var mockVibrator: Vibrator

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
        mockVibrator = mock(Vibrator::class.java)

        `when`(mockContext.getSystemService(Context.VIBRATOR_SERVICE))
            .thenReturn(mockVibrator)
    }

    @Test
    fun vibrate_success_does_not_crash() {
        try {
            HapticFeedback.vibrateSuccess(mockContext)
        } catch (e: Exception) {
            fail("vibrateSuccess threw exception: ${e.message}")
        }
    }

    @Test
    fun vibrate_warning_does_not_crash() {
        try {
            HapticFeedback.vibrateWarning(mockContext)
        } catch (e: Exception) {
            fail("vibrateWarning threw exception: ${e.message}")
        }
    }

    @Test
    fun vibrate_heavy_does_not_crash() {
        try {
            HapticFeedback.vibrateHeavy(mockContext)
        } catch (e: Exception) {
            fail("vibrateHeavy threw exception: ${e.message}")
        }
    }

    @Test
    fun handles_missing_vibrator_service() {
        val contextNoService = mock(Context::class.java)
        `when`(contextNoService.getSystemService(Context.VIBRATOR_SERVICE))
            .thenReturn(null)

        // Should not crash
        HapticFeedback.vibrateSuccess(contextNoService)
        HapticFeedback.vibrateWarning(contextNoService)
        HapticFeedback.vibrateHeavy(contextNoService)
    }
}


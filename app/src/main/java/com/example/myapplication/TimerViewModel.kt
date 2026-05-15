package com.example.myapplication

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.myapplication.utils.NotificationHelper
import com.example.myapplication.utils.HapticFeedback

data class TimerState(
    val workMinutes: Int = 25,
    val breakMinutes: Int = 5,
    val timeLeft: Int = 25 * 60,
    val isRunning: Boolean = false,
    val isWorkSession: Boolean = true,
    val sessionsCompleted: Int = 0,
    val totalFocusMinutes: Int = 0
)

class TimerViewModel(private val context: Context? = null) : ViewModel() {
    private val _state = MutableStateFlow(TimerState())
    val state: StateFlow<TimerState> = _state

    private var timerHandler: Handler? = null
    private var timerRunnable: Runnable? = null

    init {
        try {
            context?.let { NotificationHelper.createNotificationChannel(it) }
        } catch (e: Exception) {
            android.util.Log.e("TimerViewModel", "Error creating notification channel: ${e.message}")
        }
    }

    fun startTimer() {
        val currentState = _state.value
        if (currentState.isRunning) return

        _state.value = currentState.copy(isRunning = true)

        if (timerHandler == null) {
            timerHandler = Handler(Looper.getMainLooper())
        }

        timerRunnable = object : Runnable {
            override fun run() {
                val state = _state.value
                if (state.isRunning && state.timeLeft > 0) {
                    _state.value = state.copy(timeLeft = state.timeLeft - 1)
                    timerHandler?.postDelayed(this, 1000)
                } else if (state.isRunning && state.timeLeft == 0) {
                    timerHandler?.removeCallbacks(this)
                    switchSession()
                }
            }
        }
        timerHandler?.post(timerRunnable!!)
    }

    fun pauseTimer() {
        val currentState = _state.value
        if (!currentState.isRunning) return

        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        _state.value = currentState.copy(isRunning = false)
    }

    fun resetTimer() {
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        val state = _state.value
        val resetTime = if (state.isWorkSession) {
            state.workMinutes * 60
        } else {
            state.breakMinutes * 60
        }
        _state.value = state.copy(
            timeLeft = resetTime,
            isRunning = false
        )
    }

    private fun switchSession() {
        val state = _state.value
        val newState = if (state.isWorkSession) {
            state.copy(
                isWorkSession = false,
                timeLeft = state.breakMinutes * 60,
                sessionsCompleted = state.sessionsCompleted + 1,
                totalFocusMinutes = state.totalFocusMinutes + state.workMinutes,
                isRunning = false
            )
        } else {
            state.copy(
                isWorkSession = true,
                timeLeft = state.workMinutes * 60,
                isRunning = false
            )
        }
        _state.value = newState

        // Show notification and haptic feedback
        context?.let {
            NotificationHelper.showSessionCompleteNotification(it, state.isWorkSession)
            HapticFeedback.vibrateHeavy(it)
        }
    }

    fun setWorkMinutes(minutes: Int) {
        val state = _state.value
        if (!state.isRunning) {
            val newState = state.copy(
                workMinutes = minutes,
                timeLeft = if (state.isWorkSession) minutes * 60 else state.timeLeft
            )
            _state.value = newState
        }
    }

    fun setBreakMinutes(minutes: Int) {
        val state = _state.value
        if (!state.isRunning) {
            val newState = state.copy(
                breakMinutes = minutes,
                timeLeft = if (!state.isWorkSession) minutes * 60 else state.timeLeft
            )
            _state.value = newState
        }
    }

    override fun onCleared() {
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        super.onCleared()
    }
}








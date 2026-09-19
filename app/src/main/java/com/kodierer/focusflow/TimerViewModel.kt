package com.kodierer.focusflow

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.kodierer.focusflow.data.AnalyticsRepository
import com.kodierer.focusflow.data.SessionRepository
import com.kodierer.focusflow.utils.HapticFeedback
import com.kodierer.focusflow.utils.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TimerState(
    val workMinutes: Int = 25,
    val breakMinutes: Int = 5,
    val timeLeft: Int = 25 * 60,
    val isRunning: Boolean = false,
    val isWorkSession: Boolean = true,
    val sessionsCompleted: Int = 0,
    val totalFocusMinutes: Int = 0,
    val currentStreak: Int = 0   // bonus: show motivation
)

class TimerViewModel(private val context: Context? = null) : ViewModel() {
    private val sessionRepo = context?.let { SessionRepository(it) }
    private val analyticsRepo = context?.let { AnalyticsRepository(it) }

    private val _state = MutableStateFlow(TimerState())
    val state: StateFlow<TimerState> = _state

    private var timerHandler: Handler? = null
    private var timerRunnable: Runnable? = null

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    init {
        try {
            context?.let { NotificationHelper.createNotificationChannel(it) }
            loadPersistedStats()
            restoreTimerState()
        } catch (e: Exception) {
            android.util.Log.e("TimerViewModel", "Init error: ${e.message}")
        }
    }

    private fun loadPersistedStats() {
        val today = LocalDate.now().format(dateFormatter)
        val savedDate = sessionRepo?.getTodayDate() ?: ""

        val sessions = if (savedDate == today) sessionRepo?.getSessionsCompleted() ?: 0 else 0
        val focusMin = if (savedDate == today) sessionRepo?.getTotalFocusMinutes() ?: 0 else 0

        // Bonus: load a nice streak from analytics
        val streak = analyticsRepo?.getStreak() ?: 0

        _state.value = _state.value.copy(
            sessionsCompleted = sessions,
            totalFocusMinutes = focusMin,
            currentStreak = streak
        )
    }

    private fun restoreTimerState() {
        val saved = sessionRepo?.getTimerState() ?: return
        _state.value = _state.value.copy(
            timeLeft = saved.timeLeft,
            isWorkSession = saved.isWorkSession,
            workMinutes = saved.workMinutes,
            breakMinutes = saved.breakMinutes,
            isRunning = false
        )

        if (saved.isRunning) {
            startTimer()
        }
    }

    private fun persistTimerState(state: TimerState = _state.value, isRunningOverride: Boolean? = null) {
        sessionRepo?.saveTimerState(
            timeLeft = state.timeLeft,
            isWorkSession = state.isWorkSession,
            workMinutes = state.workMinutes,
            breakMinutes = state.breakMinutes,
            isRunning = isRunningOverride ?: state.isRunning
        )
    }

    fun startTimer() {
        val currentState = _state.value
        if (currentState.isRunning) return

        val startedState = currentState.copy(isRunning = true)
        _state.value = startedState
        persistTimerState(startedState)

        if (timerHandler == null) {
            timerHandler = Handler(Looper.getMainLooper())
        }

        timerRunnable = object : Runnable {
            override fun run() {
                val state = _state.value
                if (state.isRunning && state.timeLeft > 0) {
                    val updatedState = state.copy(timeLeft = state.timeLeft - 1)
                    _state.value = updatedState
                    persistTimerState(updatedState)
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
        val pausedState = currentState.copy(isRunning = false)
        _state.value = pausedState
        persistTimerState(pausedState)
    }

    fun resetTimer() {
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        val state = _state.value
        val resetTime = if (state.isWorkSession) {
            state.workMinutes * 60
        } else {
            state.breakMinutes * 60
        }
        val resetState = state.copy(
            timeLeft = resetTime,
            isRunning = false
        )
        _state.value = resetState
        persistTimerState(resetState)
    }

    fun toggleSession() {
        val state = _state.value
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        
        val newState = if (state.isWorkSession) {
            state.copy(
                isWorkSession = false,
                timeLeft = state.breakMinutes * 60,
                isRunning = false  // Let startTimer() handle the running state
            )
        } else {
            state.copy(
                isWorkSession = true,
                timeLeft = state.workMinutes * 60,
                isRunning = false  // Let startTimer() handle the running state
            )
        }
        _state.value = newState
        persistTimerState(newState)
        startTimer()  // This will set isRunning=true and start the timer
    }

    private fun switchSession() {
        val state = _state.value
        val isFinishingWork = state.isWorkSession

        val newSessions = if (isFinishingWork) state.sessionsCompleted + 1 else state.sessionsCompleted
        val newFocusMin = if (isFinishingWork) state.totalFocusMinutes + state.workMinutes else state.totalFocusMinutes

        val newState = if (isFinishingWork) {
            state.copy(
                isWorkSession = false,
                timeLeft = state.breakMinutes * 60,
                sessionsCompleted = newSessions,
                totalFocusMinutes = newFocusMin,
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
        persistTimerState(newState)

        // === PERSISTENCE: Save progress (the big attractiveness win - stats survive restarts!) ===
        if (isFinishingWork) {
            val today = LocalDate.now().format(dateFormatter)
            sessionRepo?.let { repo ->
                repo.incrementSessionsCompleted()
                repo.incrementTotalFocusMinutes(state.workMinutes)
                repo.saveTodayDate(today)
            }
            analyticsRepo?.recordSession(state.workMinutes, state.breakMinutes)
        }

        // Delight: notification + strong haptic
        context?.let {
            NotificationHelper.showSessionCompleteNotification(it, state.isWorkSession)
            HapticFeedback.vibrateHeavy(it)
        }

        // Auto-continue
        startTimer()
    }

    fun setWorkMinutes(minutes: Int) {
        val state = _state.value
        if (!state.isRunning) {
            val newState = state.copy(
                workMinutes = minutes,
                timeLeft = if (state.isWorkSession) minutes * 60 else state.timeLeft
            )
            _state.value = newState
            persistTimerState(newState)
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
            persistTimerState(newState)
        }
    }

    override fun onCleared() {
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        persistTimerState()
        super.onCleared()
    }
}








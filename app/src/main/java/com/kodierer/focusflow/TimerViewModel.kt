package com.kodierer.focusflow

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.kodierer.focusflow.data.AnalyticsRepository
import com.kodierer.focusflow.data.PersistedTimerState
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

    private data class RestoredTimerResult(
        val state: TimerState,
        val completedWorkSessions: Int
    )

    init {
        try {
            context?.let { NotificationHelper.createNotificationChannel(it) }
            restoreTimerState()
            loadPersistedStats()
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

    fun startTimer() {
        val currentState = _state.value
        if (currentState.isRunning) return

        _state.value = currentState.copy(isRunning = true)
        saveCurrentTimerState()

        if (timerHandler == null) {
            timerHandler = createMainHandlerOrNull()
        }

        if (timerHandler == null) {
            return
        }

        timerRunnable = object : Runnable {
            override fun run() {
                val state = _state.value
                if (state.isRunning && state.timeLeft > 0) {
                    _state.value = state.copy(timeLeft = state.timeLeft - 1)
                    saveCurrentTimerState()
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
        saveCurrentTimerState()
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
        saveCurrentTimerState()
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
        saveCurrentTimerState()
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
        saveCurrentTimerState()

        // === PERSISTENCE: Save progress (the big attractiveness win - stats survive restarts!) ===
        if (isFinishingWork) {
            persistCompletedWorkSession(state.workMinutes, state.breakMinutes)
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
            saveCurrentTimerState()
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
            saveCurrentTimerState()
        }
    }

    fun persistTimerState() {
        saveCurrentTimerState()
    }

    override fun onCleared() {
        saveCurrentTimerState()
        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        super.onCleared()
    }

    private fun restoreTimerState() {
        val persistedState = sessionRepo?.getTimerState() ?: return
        val restoredState = restoreTimerStateWithElapsed(persistedState)

        if (restoredState.completedWorkSessions > 0) {
            repeat(restoredState.completedWorkSessions) {
                persistCompletedWorkSession(
                    restoredState.state.workMinutes,
                    restoredState.state.breakMinutes
                )
            }
        }

        _state.value = _state.value.copy(
            workMinutes = restoredState.state.workMinutes,
            breakMinutes = restoredState.state.breakMinutes,
            timeLeft = restoredState.state.timeLeft,
            isRunning = restoredState.state.isRunning,
            isWorkSession = restoredState.state.isWorkSession,
            sessionsCompleted = restoredState.state.sessionsCompleted,
            totalFocusMinutes = restoredState.state.totalFocusMinutes
        )
        saveCurrentTimerState()
        resumeTimerIfNeeded()
    }

    private fun restoreTimerStateWithElapsed(
        persistedState: PersistedTimerState,
        nowMillis: Long = System.currentTimeMillis()
    ): RestoredTimerResult {
        var restoredState = TimerState(
            workMinutes = persistedState.workMinutes,
            breakMinutes = persistedState.breakMinutes,
            timeLeft = persistedState.timeLeft.coerceAtLeast(0),
            isRunning = persistedState.isRunning,
            isWorkSession = persistedState.isWorkSession
        )
        var completedWorkSessions = 0

        if (!persistedState.isRunning) {
            return RestoredTimerResult(restoredState, completedWorkSessions)
        }

        var elapsedSeconds = ((nowMillis - persistedState.savedAtMillis).coerceAtLeast(0L) / 1000L).toInt()

        while (elapsedSeconds > 0 && restoredState.timeLeft > 0) {
            if (elapsedSeconds < restoredState.timeLeft) {
                restoredState = restoredState.copy(timeLeft = restoredState.timeLeft - elapsedSeconds)
                elapsedSeconds = 0
            } else {
                elapsedSeconds -= restoredState.timeLeft
                val nextSessionSeconds = if (restoredState.isWorkSession) {
                    completedWorkSessions++
                    restoredState = restoredState.copy(
                        isWorkSession = false,
                        timeLeft = (restoredState.breakMinutes * 60).coerceAtLeast(0),
                        sessionsCompleted = restoredState.sessionsCompleted + 1,
                        totalFocusMinutes = restoredState.totalFocusMinutes + restoredState.workMinutes,
                        isRunning = true
                    )
                    restoredState.timeLeft
                } else {
                    restoredState = restoredState.copy(
                        isWorkSession = true,
                        timeLeft = (restoredState.workMinutes * 60).coerceAtLeast(0),
                        isRunning = true
                    )
                    restoredState.timeLeft
                }

                if (nextSessionSeconds <= 0) {
                    break
                }
            }
        }

        return RestoredTimerResult(restoredState, completedWorkSessions)
    }

    private fun persistCompletedWorkSession(workMinutes: Int, breakMinutes: Int) {
        val today = LocalDate.now().format(dateFormatter)
        sessionRepo?.let { repo ->
            repo.incrementSessionsCompleted()
            repo.incrementTotalFocusMinutes(workMinutes)
            repo.saveTodayDate(today)
        }
        analyticsRepo?.recordSession(workMinutes, breakMinutes)
    }

    private fun saveCurrentTimerState() {
        sessionRepo?.saveTimerState(
            PersistedTimerState(
                workMinutes = _state.value.workMinutes,
                breakMinutes = _state.value.breakMinutes,
                timeLeft = _state.value.timeLeft,
                isRunning = _state.value.isRunning,
                isWorkSession = _state.value.isWorkSession,
                savedAtMillis = System.currentTimeMillis()
            )
        )
    }

    private fun resumeTimerIfNeeded() {
        val currentState = _state.value
        if (!currentState.isRunning) return

        if (timerHandler == null) {
            timerHandler = createMainHandlerOrNull()
        }

        if (timerHandler == null) {
            return
        }

        timerRunnable?.let { timerHandler?.removeCallbacks(it) }
        timerRunnable = object : Runnable {
            override fun run() {
                val state = _state.value
                if (state.isRunning && state.timeLeft > 0) {
                    _state.value = state.copy(timeLeft = state.timeLeft - 1)
                    saveCurrentTimerState()
                    timerHandler?.postDelayed(this, 1000)
                } else if (state.isRunning && state.timeLeft == 0) {
                    timerHandler?.removeCallbacks(this)
                    switchSession()
                }
            }
        }
        timerHandler?.postDelayed(timerRunnable!!, 1000)
    }

    private fun createMainHandlerOrNull(): Handler? {
        return try {
            Handler(Looper.getMainLooper())
        } catch (_: Throwable) {
            null
        }
    }
}







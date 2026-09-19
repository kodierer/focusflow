package com.kodierer.focusflow.data

import android.content.Context
import android.content.SharedPreferences

data class PersistedTimerState(
    val workMinutes: Int,
    val breakMinutes: Int,
    val timeLeft: Int,
    val isRunning: Boolean,
    val isWorkSession: Boolean,
    val savedAtMillis: Long
)

class SessionRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("focus_timer_prefs", Context.MODE_PRIVATE)

    fun incrementSessionsCompleted() {
        val current = getSessionsCompleted()
        sharedPreferences.edit().putInt(SESSIONS_KEY, current + 1).apply()
    }

    fun getSessionsCompleted(): Int {
        return sharedPreferences.getInt(SESSIONS_KEY, 0)
    }

    fun incrementTotalFocusMinutes(minutes: Int) {
        val current = getTotalFocusMinutes()
        sharedPreferences.edit().putInt(FOCUS_MINUTES_KEY, current + minutes).apply()
    }

    fun getTotalFocusMinutes(): Int {
        return sharedPreferences.getInt(FOCUS_MINUTES_KEY, 0)
    }

    fun saveTodayDate(date: String) {
        sharedPreferences.edit().putString(TODAY_DATE_KEY, date).apply()
    }

    fun getTodayDate(): String {
        return sharedPreferences.getString(TODAY_DATE_KEY, "") ?: ""
    }

    fun resetDailyStats() {
        sharedPreferences.edit().apply {
            putInt(SESSIONS_KEY, 0)
            putInt(FOCUS_MINUTES_KEY, 0)
            putString(TODAY_DATE_KEY, "")
        }.apply()
    }

    fun saveTimerState(timerState: PersistedTimerState) {
        sharedPreferences.edit().apply {
            putInt(TIMER_WORK_MINUTES_KEY, timerState.workMinutes)
            putInt(TIMER_BREAK_MINUTES_KEY, timerState.breakMinutes)
            putInt(TIMER_TIME_LEFT_KEY, timerState.timeLeft)
            putBoolean(TIMER_IS_RUNNING_KEY, timerState.isRunning)
            putBoolean(TIMER_IS_WORK_SESSION_KEY, timerState.isWorkSession)
            putLong(TIMER_SAVED_AT_KEY, timerState.savedAtMillis)
        }.apply()
    }

    fun getTimerState(): PersistedTimerState? {
        if (!sharedPreferences.contains(TIMER_TIME_LEFT_KEY)) {
            return null
        }

        return PersistedTimerState(
            workMinutes = sharedPreferences.getInt(TIMER_WORK_MINUTES_KEY, 25),
            breakMinutes = sharedPreferences.getInt(TIMER_BREAK_MINUTES_KEY, 5),
            timeLeft = sharedPreferences.getInt(TIMER_TIME_LEFT_KEY, 25 * 60),
            isRunning = sharedPreferences.getBoolean(TIMER_IS_RUNNING_KEY, false),
            isWorkSession = sharedPreferences.getBoolean(TIMER_IS_WORK_SESSION_KEY, true),
            savedAtMillis = sharedPreferences.getLong(TIMER_SAVED_AT_KEY, 0L)
        )
    }

    companion object {
        private const val SESSIONS_KEY = "sessions_completed"
        private const val FOCUS_MINUTES_KEY = "total_focus_minutes"
        private const val TODAY_DATE_KEY = "today_date"
        private const val TIMER_WORK_MINUTES_KEY = "timer_work_minutes"
        private const val TIMER_BREAK_MINUTES_KEY = "timer_break_minutes"
        private const val TIMER_TIME_LEFT_KEY = "timer_time_left"
        private const val TIMER_IS_RUNNING_KEY = "timer_is_running"
        private const val TIMER_IS_WORK_SESSION_KEY = "timer_is_work_session"
        private const val TIMER_SAVED_AT_KEY = "timer_saved_at"
    }
}

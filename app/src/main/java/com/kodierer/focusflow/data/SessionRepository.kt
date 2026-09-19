package com.kodierer.focusflow.data

import android.content.Context
import android.content.SharedPreferences

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

    fun saveTimerState(
        timeLeft: Int,
        isWorkSession: Boolean,
        workMinutes: Int,
        breakMinutes: Int,
        isRunning: Boolean
    ) {
        sharedPreferences.edit().apply {
            putInt(TIMER_TIME_LEFT_KEY, timeLeft)
            putBoolean(TIMER_IS_WORK_SESSION_KEY, isWorkSession)
            putInt(TIMER_WORK_MINUTES_KEY, workMinutes)
            putInt(TIMER_BREAK_MINUTES_KEY, breakMinutes)
            putBoolean(TIMER_IS_RUNNING_KEY, isRunning)
        }.apply()
    }

    fun getTimerState(): SavedTimerState? {
        val timeLeft = sharedPreferences.getInt(TIMER_TIME_LEFT_KEY, -1)
        if (timeLeft < 0) return null

        return SavedTimerState(
            timeLeft = timeLeft,
            isWorkSession = sharedPreferences.getBoolean(TIMER_IS_WORK_SESSION_KEY, true),
            workMinutes = sharedPreferences.getInt(TIMER_WORK_MINUTES_KEY, 25),
            breakMinutes = sharedPreferences.getInt(TIMER_BREAK_MINUTES_KEY, 5),
            isRunning = sharedPreferences.getBoolean(TIMER_IS_RUNNING_KEY, false)
        )
    }

    fun clearTimerState() {
        sharedPreferences.edit().apply {
            remove(TIMER_TIME_LEFT_KEY)
            remove(TIMER_IS_WORK_SESSION_KEY)
            remove(TIMER_WORK_MINUTES_KEY)
            remove(TIMER_BREAK_MINUTES_KEY)
            remove(TIMER_IS_RUNNING_KEY)
        }.apply()
    }

    data class SavedTimerState(
        val timeLeft: Int,
        val isWorkSession: Boolean,
        val workMinutes: Int,
        val breakMinutes: Int,
        val isRunning: Boolean
    )

    companion object {
        private const val SESSIONS_KEY = "sessions_completed"
        private const val FOCUS_MINUTES_KEY = "total_focus_minutes"
        private const val TODAY_DATE_KEY = "today_date"
        private const val TIMER_TIME_LEFT_KEY = "timer_time_left"
        private const val TIMER_IS_WORK_SESSION_KEY = "timer_is_work_session"
        private const val TIMER_WORK_MINUTES_KEY = "timer_work_minutes"
        private const val TIMER_BREAK_MINUTES_KEY = "timer_break_minutes"
        private const val TIMER_IS_RUNNING_KEY = "timer_is_running"
    }
}

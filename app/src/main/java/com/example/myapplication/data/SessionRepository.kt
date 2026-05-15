package com.example.myapplication.data

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

    companion object {
        private const val SESSIONS_KEY = "sessions_completed"
        private const val FOCUS_MINUTES_KEY = "total_focus_minutes"
        private const val TODAY_DATE_KEY = "today_date"
    }
}

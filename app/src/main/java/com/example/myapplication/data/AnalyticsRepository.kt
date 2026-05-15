package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DailyStats(
    val date: String,
    val sessionsCompleted: Int,
    val focusMinutes: Int,
    val breakMinutes: Int
)

class AnalyticsRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("focus_timer_analytics", Context.MODE_PRIVATE)

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun recordSession(focusMinutes: Int, breakMinutes: Int) {
        val today = LocalDate.now().format(formatter)
        val key = "session_$today"
        
        val currentCount = sharedPreferences.getInt(key, 0)
        val focusKey = "focus_$today"
        val breakKey = "break_$today"
        
        val currentFocusMinutes = sharedPreferences.getInt(focusKey, 0)
        val currentBreakMinutes = sharedPreferences.getInt(breakKey, 0)
        
        sharedPreferences.edit().apply {
            putInt(key, currentCount + 1)
            putInt(focusKey, currentFocusMinutes + focusMinutes)
            putInt(breakKey, currentBreakMinutes + breakMinutes)
        }.apply()
    }

    fun getTodayStats(): DailyStats {
        val today = LocalDate.now().format(formatter)
        val sessionsKey = "session_$today"
        val focusKey = "focus_$today"
        val breakKey = "break_$today"
        
        return DailyStats(
            date = today,
            sessionsCompleted = sharedPreferences.getInt(sessionsKey, 0),
            focusMinutes = sharedPreferences.getInt(focusKey, 0),
            breakMinutes = sharedPreferences.getInt(breakKey, 0)
        )
    }

    fun getWeeklyStats(): List<DailyStats> {
        val stats = mutableListOf<DailyStats>()
        for (daysAgo in 0..6) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val sessionsKey = "session_$date"
            val focusKey = "focus_$date"
            val breakKey = "break_$date"
            
            stats.add(
                DailyStats(
                    date = date,
                    sessionsCompleted = sharedPreferences.getInt(sessionsKey, 0),
                    focusMinutes = sharedPreferences.getInt(focusKey, 0),
                    breakMinutes = sharedPreferences.getInt(breakKey, 0)
                )
            )
        }
        return stats.reversed()
    }

    fun getStreak(): Int {
        var streak = 0
        for (daysAgo in 0..365) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val sessionsKey = "session_$date"
            val sessions = sharedPreferences.getInt(sessionsKey, 0)
            
            if (sessions > 0) {
                streak++
            } else if (daysAgo > 0) {
                break
            }
        }
        return streak
    }

    fun getLongestStreak(): Int {
        var longestStreak = 0
        var currentStreak = 0
        
        for (daysAgo in 0..365) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val sessionsKey = "session_$date"
            val sessions = sharedPreferences.getInt(sessionsKey, 0)
            
            if (sessions > 0) {
                currentStreak++
                longestStreak = maxOf(longestStreak, currentStreak)
            } else {
                currentStreak = 0
            }
        }
        
        return longestStreak
    }

    fun getTotalSessions(): Int {
        var total = 0
        for (daysAgo in 0..365) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val sessionsKey = "session_$date"
            val sessions = sharedPreferences.getInt(sessionsKey, 0)
            total += sessions
        }
        return total
    }

    fun getTotalFocusHours(): Double {
        var total = 0
        for (daysAgo in 0..365) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val focusKey = "focus_$date"
            val minutes = sharedPreferences.getInt(focusKey, 0)
            total += minutes
        }
        return total / 60.0
    }

    fun getAverageFocusPerSession(): Int {
        val totalSession = getTotalSessions()
        if (totalSession == 0) return 0
        
        var totalMinutes = 0
        for (daysAgo in 0..365) {
            val date = LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
            val focusKey = "focus_$date"
            val minutes = sharedPreferences.getInt(focusKey, 0)
            totalMinutes += minutes
        }
        
        return totalMinutes / totalSession
    }

    fun resetAllData() {
        sharedPreferences.edit().clear().apply()
    }
}

package com.kodierer.focusflow.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.kodierer.focusflow.utils.NotificationHelper

class TimerForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return try {
            val shouldStop = intent?.getBooleanExtra(EXTRA_STOP_SERVICE, false) ?: false
            if (shouldStop) {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                START_NOT_STICKY
            } else {
                val timeLeftSeconds = intent?.getIntExtra(EXTRA_TIME_LEFT_SECONDS, 0) ?: 0
                val isWorkSession = intent?.getBooleanExtra(EXTRA_IS_WORK_SESSION, true) ?: true

                NotificationHelper.createNotificationChannel(this)
                val notification = NotificationHelper.createOngoingTimerNotification(
                    context = this,
                    timeLeftSeconds = timeLeftSeconds,
                    isWorkSession = isWorkSession
                )
                startForeground(NotificationHelper.ONGOING_NOTIFICATION_ID, notification)
                START_STICKY
            }
        } catch (_: Exception) {
            START_NOT_STICKY
        }
    }

    companion object {
        private const val EXTRA_TIME_LEFT_SECONDS = "extra_time_left_seconds"
        private const val EXTRA_IS_WORK_SESSION = "extra_is_work_session"
        private const val EXTRA_STOP_SERVICE = "extra_stop_service"

        fun startOrUpdate(context: Context, timeLeftSeconds: Int, isWorkSession: Boolean) {
            try {
                val intent = Intent(context, TimerForegroundService::class.java).apply {
                    putExtra(EXTRA_TIME_LEFT_SECONDS, timeLeftSeconds)
                    putExtra(EXTRA_IS_WORK_SESSION, isWorkSession)
                    putExtra(EXTRA_STOP_SERVICE, false)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (_: Exception) {
            }
        }

        fun stop(context: Context) {
            try {
                val intent = Intent(context, TimerForegroundService::class.java).apply {
                    putExtra(EXTRA_STOP_SERVICE, true)
                }
                context.startService(intent)
            } catch (_: Exception) {
                NotificationHelper.cancelOngoingTimerNotification(context)
            }
        }
    }
}




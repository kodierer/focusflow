package com.kodierer.focusflow.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kodierer.focusflow.MainActivity
import java.util.Locale

object NotificationHelper {
    private const val COMPLETION_CHANNEL_ID = "focus_timer_completion_channel"
    private const val ONGOING_CHANNEL_ID = "focus_timer_ongoing_channel_v3"
    private const val COMPLETION_NOTIFICATION_ID = 1001
    const val ONGOING_NOTIFICATION_ID = 1002
    // Value matches Notification.FOREGROUND_SERVICE_IMMEDIATE for API 31+.
    private const val FOREGROUND_SERVICE_IMMEDIATE_BEHAVIOR = 1
    private const val COMPLETION_CHANNEL_NAME = "Focus Timer Abschluss"
    private const val ONGOING_CHANNEL_NAME = "Focus Timer Laufend"

    fun createNotificationChannel(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val completionChannel = NotificationChannel(
                    COMPLETION_CHANNEL_ID,
                    COMPLETION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifications für Focus Timer Sessions"
                    enableVibration(true)
                    enableLights(true)
                }

                val ongoingChannel = NotificationChannel(
                    ONGOING_CHANNEL_ID,
                    ONGOING_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Laufende Timer-Anzeige in der Statusleiste"
                    setShowBadge(false)
                    enableVibration(false)
                    lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                notificationManager?.createNotificationChannel(completionChannel)
                notificationManager?.createNotificationChannel(ongoingChannel)
            }
        } catch (e: Exception) {
            android.util.Log.e("NotificationHelper", "Error creating channel: ${e.message}")
        }
    }

    fun createOngoingTimerNotification(
        context: Context,
        timeLeftSeconds: Int,
        isWorkSession: Boolean
    ) = NotificationCompat.Builder(context, ONGOING_CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
        .setContentTitle(if (isWorkSession) "Fokus laeuft" else "Pause laeuft")
        .setContentText("Verbleibend: ${formatTime(timeLeftSeconds)}")
        .setContentIntent(createMainActivityPendingIntent(context))
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setOngoing(true)
        .setOnlyAlertOnce(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_SERVICE)
        .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE_BEHAVIOR)
        .build()

    fun cancelOngoingTimerNotification(context: Context) {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.cancel(ONGOING_NOTIFICATION_ID)
        } catch (e: Exception) {
            android.util.Log.e("NotificationHelper", "Error canceling ongoing notification: ${e.message}")
        }
    }

    fun showSessionCompleteNotification(context: Context, isWorkSession: Boolean) {
        try {
            createNotificationChannel(context)

            val title = if (isWorkSession) 
                "✅ Arbeitszeit vorbei!" 
            else 
                " Pause beendet!"
                
            val message = if (isWorkSession)
                "Kurze Pause verdient! Tippe um fortzufahren."
            else
                "Bereit für eine neue Fokus-Sitzung?"

            val builder = NotificationCompat.Builder(context, COMPLETION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(createMainActivityPendingIntent(context))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)

            if (isWorkSession) {
                builder.setColor(0xFF43A047.toInt())  // Grün
            } else {
                builder.setColor(0xFF1E88E5.toInt())  // Blau
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(COMPLETION_NOTIFICATION_ID, builder.build())
        } catch (e: Exception) {
            android.util.Log.e("NotificationHelper", "Error showing notification: ${e.message}")
        }
    }

    private fun createMainActivityPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun formatTime(totalSeconds: Int): String {
        val safeSeconds = totalSeconds.coerceAtLeast(0)
        val minutes = safeSeconds / 60
        val seconds = safeSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}


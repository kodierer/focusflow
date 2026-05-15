package com.example.myapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "focus_timer_channel"
    private const val NOTIFICATION_ID = 1001
    private const val CHANNEL_NAME = "Focus Timer Notifications"

    fun createNotificationChannel(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    description = "Notifications für Focus Timer Sessions"
                    enableVibration(true)
                    enableLights(true)
                }
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                notificationManager?.createNotificationChannel(channel)
            }
        } catch (e: Exception) {
            android.util.Log.e("NotificationHelper", "Error creating channel: ${e.message}")
        }
    }

    fun showSessionCompleteNotification(context: Context, isWorkSession: Boolean) {
        try {
            createNotificationChannel(context)

            val title = if (isWorkSession) 
                "✅ Arbeitszeit vorbei!" 
            else 
                "💪 Pause beendet!"
                
            val message = if (isWorkSession)
                "Kurze Pause verdient! Tippe um fortzufahren."
            else
                "Bereit für eine neue Fokus-Sitzung?"

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            
            val pendingIntent = PendingIntent.getActivity(
                context, 
                0, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)

            if (isWorkSession) {
                builder.setColor(0xFF43A047.toInt())  // Grün
            } else {
                builder.setColor(0xFF1E88E5.toInt())  // Blau
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(NOTIFICATION_ID, builder.build())
        } catch (e: Exception) {
            android.util.Log.e("NotificationHelper", "Error showing notification: ${e.message}")
        }
    }
}



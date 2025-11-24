package com.marossolutions.checktheflight.notification

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import com.marossolutions.checktheflight.notification.NotificationConstants.CHANNEL_ID

private const val CHANNEL_DESCRIPTION = "Notification channel description"
private const val CHANNEL_NAME = "Notification channel name"

internal fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            android.app.NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
        }.also {
            val notificationManager: android.app.NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(it)
        }
    }
}
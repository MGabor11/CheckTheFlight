package com.marossolutions.checktheflight.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.marossolutions.checktheflight.R
import com.marossolutions.checktheflight.notification.NotificationConstants.CHANNEL_ID

private const val NOTIFICATION_ID = 1

actual class NotificationManager(
    private val context: Context
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    actual fun showNotification(
        title: String,
        description: String
    ) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (areNotificationEnabled) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }
    }

    private val areNotificationEnabled
        get() = NotificationManagerCompat
            .from(context)
            .areNotificationsEnabled()
}
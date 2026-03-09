package com.marossolutions.checktheflight.notification

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.marossolutions.checktheflight.R
import com.marossolutions.checktheflight.notification.NotificationConstants.CHANNEL_ID
import com.marossolutions.domain.provider.NotificationIdProvider
import com.marossolutions.flightsync.FlightSyncNotifier

private const val DEFAULT_NOTIFICATION_ID = 1

actual class NotificationManager(
    private val context: Context,
    private val notificationIdProvider: NotificationIdProvider,
) : FlightSyncNotifier {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    actual override fun showNotification(
        title: String,
        description: String,
    ) {
        sendNotification(title, description)
    }

    private val areNotificationEnabled
        get() = NotificationManagerCompat
            .from(context)
            .areNotificationsEnabled()

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    actual override suspend fun showFlightInfoNotification(title: String, description: String) {
        val notificationId = notificationIdProvider.getNotificationId()
        sendNotification(title = title, description = description, notificationId = notificationId)
    }

    actual fun requestNotificationPermission() {
        // TODO
    }

    actual fun isNotificationPermissionGranted(): Boolean {
        // TODO
        return true
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun sendNotification(title: String, description: String, notificationId: Int? = null) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(description)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (areNotificationEnabled) {
            NotificationManagerCompat.from(context).notify(
                notificationId ?: DEFAULT_NOTIFICATION_ID,
                builder.build()
            )
        }
    }
}

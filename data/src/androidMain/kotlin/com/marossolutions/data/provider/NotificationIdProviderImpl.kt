package com.marossolutions.data.provider

import com.marossolutions.data.datastore.NotificationIdPreferences
import com.marossolutions.domain.provider.NotificationIdProvider

private const val MIN_NOTIFICATION_ID = 1
private const val MAX_NOTIFICATION_ID = 200

class NotificationIdProviderImpl(
    private val notificationIdPreferences: NotificationIdPreferences
) : NotificationIdProvider {

    override suspend fun getNotificationId(): Int {
        val notificationId = notificationIdPreferences.getNotificationId()
        val newNotificationId = if (notificationId == MAX_NOTIFICATION_ID) {
            MIN_NOTIFICATION_ID
        } else {
            notificationId + 1
        }

        notificationIdPreferences.setNotificationId(newNotificationId)
        return newNotificationId
    }
}
package com.marossolutions.checktheflight.notification

expect class NotificationManager {

    fun showNotification(
        title: String,
        description: String
    )

    suspend fun showFlightInfoNotification(
        title: String,
        description: String
    )

    fun requestNotificationPermission()

    fun isNotificationPermissionGranted(): Boolean
}

package com.marossolutions.checktheflight.notification

import com.marossolutions.flightsync.FlightSyncNotifier

expect class NotificationManager : FlightSyncNotifier {

    override fun showNotification(
        title: String,
        description: String
    )

    override suspend fun showFlightInfoNotification(
        title: String,
        description: String
    )

    fun requestNotificationPermission()

    fun isNotificationPermissionGranted(): Boolean
}

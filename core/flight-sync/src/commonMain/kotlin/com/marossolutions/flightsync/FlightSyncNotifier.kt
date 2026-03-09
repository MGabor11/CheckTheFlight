package com.marossolutions.flightsync

/**
 * Notifier interface used by the flight sync module to send notifications.
 * Implemented by the app module where platform-specific notification APIs are available.
 */
interface FlightSyncNotifier {

    fun showNotification(
        title: String,
        description: String,
    )

    suspend fun showFlightInfoNotification(
        title: String,
        description: String,
    )
}


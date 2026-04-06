package com.marossolutions.flightsync

expect class FlightInfoSyncManager {

    suspend fun startFlightBackgroundSync()

    suspend fun stopFlightBackgroundSync()

    suspend fun isFlightBackgroundSyncRunning(): Boolean
}


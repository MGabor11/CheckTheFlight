package com.marossolutions.checktheflight.manager

expect class FlightSyncManager {

    suspend fun startFlightBackgroundSync()

    suspend fun stopFlightBackgroundSync()

    suspend fun isFlightBackgroundSyncRunning(): Boolean
}
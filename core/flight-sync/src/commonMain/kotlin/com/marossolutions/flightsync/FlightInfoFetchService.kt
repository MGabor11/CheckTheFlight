package com.marossolutions.flightsync

interface FlightInfoFetchService {
    suspend fun fetchFlightInfo(withNotification: Boolean = false)
}


package com.marossolutions.checktheflight.service

interface FlightInfoFetchService {
    suspend fun fetchFlightInfo(withNotification: Boolean = false)
}

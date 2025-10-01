package com.marossolutions.data.network

internal interface AirlineRemoteService {

    suspend fun getAirline(icao: String): AirlineResponse?
}
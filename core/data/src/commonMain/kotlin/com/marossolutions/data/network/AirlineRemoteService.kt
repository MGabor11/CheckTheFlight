package com.marossolutions.data.network

internal interface AirlineApi {

    suspend fun getAirline(icao: String): AirlineResponse?
}
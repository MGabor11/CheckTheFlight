package com.marossolutions.data.network

internal interface AirportRemoteService {

    suspend fun getAirports(countryCode: String): List<AirportResponse>

    suspend fun getAirportByICAO(icao: String): AirportResponse
}
package com.marossolutions.data.network

internal interface AirportApi {

    suspend fun getAirports(countryCode: String): List<AirportResponse>

    suspend fun getAirportByICAO(icao: String): AirportResponse
}
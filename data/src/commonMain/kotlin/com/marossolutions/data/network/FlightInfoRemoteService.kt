package com.marossolutions.data.network

interface FlightInfoApi {

    suspend fun getFlightInfo(flightIata: String): FlightInfoResponse
}
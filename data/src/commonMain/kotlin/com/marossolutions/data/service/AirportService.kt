package com.marossolutions.data.service

import com.marossolutions.domain.model.Airport

interface AirportService {

    suspend fun getAirportByIcao(icao: String): Airport

    suspend fun getAirportsByIcaos(icaos: List<String>): List<Airport>
}
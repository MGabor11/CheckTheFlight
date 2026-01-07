package com.marossolutions.domain.repository

import com.marossolutions.domain.model.Airport
import kotlinx.coroutines.flow.StateFlow

interface AirportRepository {

    val airports: StateFlow<List<Airport>>

    val airportDetail: StateFlow<Airport?>

    suspend fun fetchAirports()

    suspend fun refreshAirports()

    suspend fun fetchAirportDetails(icao: String)

    fun clearSelectedAirport()
}

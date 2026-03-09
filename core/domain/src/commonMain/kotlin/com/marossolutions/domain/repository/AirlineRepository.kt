package com.marossolutions.domain.repository

import com.marossolutions.domain.model.Airline
import kotlinx.coroutines.flow.StateFlow

interface AirlineRepository {

    val airlines: StateFlow<List<Airline>>

    val airlineDetail: StateFlow<Airline?>

    suspend fun fetchAirlines()

    suspend fun fetchAirlineDetails(icao: String)

    fun clearSelectedAirline()
}

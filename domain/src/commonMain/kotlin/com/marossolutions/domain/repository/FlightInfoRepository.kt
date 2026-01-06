package com.marossolutions.domain.repository

import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.model.FlightInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FlightInfoRepository {
    val flightNumber: Flow<String?>

    val flightInfo: Flow<FlightInfo?>

    suspend fun fetchFlightInfo(flightNumber: String)

    suspend fun setFlightNumber(flightNumber: String)

    fun clearFlightNumber()
}
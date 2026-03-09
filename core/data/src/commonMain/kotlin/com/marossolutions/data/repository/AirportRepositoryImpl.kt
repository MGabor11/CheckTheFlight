package com.marossolutions.data.repository

import com.marossolutions.data.service.AirportRemoteDataSource
import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.repository.AirportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AirportRepositoryImpl(
    private val airportRemoteDataSource: AirportRemoteDataSource,
) : AirportRepository {

    private val _airports = MutableStateFlow<List<Airport>>(emptyList())
    override val airports: StateFlow<List<Airport>> = _airports.asStateFlow()

    private val _airportDetail = MutableStateFlow<Airport?>(null)
    override val airportDetail: StateFlow<Airport?> = _airportDetail.asStateFlow()

    override suspend fun fetchAirports() {
        val airportsList = airportRemoteDataSource.getAirportsByIcaos(
            listOf(
                "LHBP",
                "KDFW",
                "RJTT",
                "EGLL"
            )
        )

        _airports.value = airportsList
    }

    override suspend fun refreshAirports() {
        _airports.value = emptyList()
        fetchAirports()
    }

    override suspend fun fetchAirportDetails(icao: String) {
        val airport = airportRemoteDataSource.getAirportByIcao(icao)
        _airportDetail.value = airport
    }

    override fun clearSelectedAirport() {
        _airportDetail.value = null
    }
}

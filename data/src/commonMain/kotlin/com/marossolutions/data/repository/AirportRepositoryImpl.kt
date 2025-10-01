package com.marossolutions.data.repository

import com.marossolutions.data.service.AirportService
import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.repository.AirportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AirportRepositoryImpl(
    private val airportService: AirportService,
) : AirportRepository {

    private val _airports = MutableStateFlow<List<Airport>>(emptyList())

    private val _selectedAirportDetail = MutableStateFlow<Airport?>(null)

    override val airports: StateFlow<List<Airport>> = _airports.asStateFlow()
    override val airportDetail: StateFlow<Airport?> = _selectedAirportDetail.asStateFlow()

    override suspend fun fetchAirports() {
        val airports = airportService.getAirportsByIcaos(
            listOf(
                "LHBP",
                "KDFW",
                "RJTT",
                "EGLL"
            )
        )

        _airports.value = airports
    }

    override suspend fun refreshAirports() {
        _airports.value = emptyList()
        fetchAirports()
    }

    override suspend fun fetchAirportDetails(icao: String) {
        val airport = airportService.getAirportByIcao(icao)
        _selectedAirportDetail.value = airport
    }

    override fun clearSelectedAirport() {
        _selectedAirportDetail.value = null
    }
}

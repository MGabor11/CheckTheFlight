package com.marossolutions.data.repository

import com.marossolutions.data.service.AirlineService
import com.marossolutions.domain.model.Airline
import com.marossolutions.domain.repository.AirlineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AirlineRepositoryImpl(
    private val airlineService: AirlineService
) : AirlineRepository {

    private val _airlines = MutableStateFlow<List<Airline>>(emptyList())

    private val _selectedAirlineDetail = MutableStateFlow<Airline?>(null)

    override val airlines: StateFlow<List<Airline>> = _airlines.asStateFlow()

    override val airlineDetail: StateFlow<Airline?> = _selectedAirlineDetail.asStateFlow()

    override suspend fun fetchAirlines() {
        val airlines = airlineService.getAirlinesByIcaos(
            listOf(
                "DLH", "RYR", "TVS", "DAL", "WZZ"
            )
        )

        _airlines.value = airlines
    }

    override suspend fun fetchAirlineDetails(icao: String) {
        val airport = airlineService.getAirlineByIcao(icao)
        _selectedAirlineDetail.value = airport
    }

    override fun clearSelectedAirline() {
        _selectedAirlineDetail.value = null
    }
}
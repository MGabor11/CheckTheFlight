package com.marossolutions.domain.usecase

import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.repository.AirportRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveAirportsUseCase(
    private val airportRepository: AirportRepository
) {
    operator fun invoke(): StateFlow<List<Airport>> = airportRepository.airports
}

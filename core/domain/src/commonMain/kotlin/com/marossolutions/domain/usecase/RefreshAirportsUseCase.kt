package com.marossolutions.domain.usecase

import com.marossolutions.domain.repository.AirportRepository

class RefreshAirportsUseCase(
    private val airportRepository: AirportRepository
) {
    suspend operator fun invoke() {
        airportRepository.refreshAirports()
    }
}

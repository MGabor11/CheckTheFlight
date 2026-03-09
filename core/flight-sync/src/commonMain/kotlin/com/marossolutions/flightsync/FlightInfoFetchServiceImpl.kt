package com.marossolutions.flightsync

import com.marossolutions.domain.model.FlightInfo
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import com.marossolutions.domain.repository.FlightInfoRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class FlightInfoFetchServiceImpl(
    private val flightInfoRepository: FlightInfoRepository,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository,
    private val notifier: FlightSyncNotifier,
) : FlightInfoFetchService {

    @OptIn(ExperimentalTime::class)
    override suspend fun fetchFlightInfo(withNotification: Boolean) {
        flightInfoFetchingSettingsRepository.setLastBackgroundFetchTime(Clock.System.now())
        val flightNumber = flightInfoRepository.flightNumber.firstOrNull()
        flightNumber?.let { number ->
            flightInfoRepository.fetchFlightInfo(number)
            if (withNotification) {
                val flightInfo = flightInfoRepository.flightInfo.firstOrNull()
                if (flightInfo != null) {
                    showFlightInfoNotification(flightInfo)
                }
            }
            flightInfoFetchingSettingsRepository.setLastSuccessfulBackgroundFetchTime(Clock.System.now())
        }
    }

    private suspend fun showFlightInfoNotification(flightInfo: FlightInfo) {
        notifier.showFlightInfoNotification(
            title = flightInfo.flightNumber,
            description = flightInfo.toString()
        )
    }
}


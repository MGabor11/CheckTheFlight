package com.marossolutions.checktheflight.service

import com.marossolutions.checktheflight.notification.NotificationManager
import com.marossolutions.domain.model.FlightInfo
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import com.marossolutions.domain.repository.FlightInfoRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class FlightInfoFetchServiceImpl(
    private val flightInfoRepository: FlightInfoRepository,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository,
    private val notificationManager: NotificationManager
) : FlightInfoFetchService {

    @OptIn(ExperimentalTime::class)
    override suspend fun fetchFlightInfo(withNotification: Boolean) {
        flightInfoFetchingSettingsRepository.setLastBackgroundFetchTime(Clock.System.now())
        val flightNumber = flightInfoRepository.flightNumber.firstOrNull()
        flightNumber?.let { flightNumber ->
            flightInfoRepository.fetchFlightInfo(flightNumber)
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
        notificationManager.showFlightInfoNotification(
            title = flightInfo.flightNumber,
            description = flightInfo.toString()
        )
    }
}

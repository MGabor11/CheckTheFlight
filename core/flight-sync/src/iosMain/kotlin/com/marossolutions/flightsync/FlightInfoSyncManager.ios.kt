package com.marossolutions.flightsync

import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import kotlinx.coroutines.flow.first

actual class FlightInfoSyncManager(
    private val backgroundTaskScheduler: BackgroundTaskScheduler,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository,
) {
    actual suspend fun startFlightBackgroundSync() {
        backgroundTaskScheduler.scheduleBackgroundRefresh()
        flightInfoFetchingSettingsRepository.setPeriodicFlightInfoFetchingEnabled(true)
    }

    actual suspend fun stopFlightBackgroundSync() {
        backgroundTaskScheduler.stopBackgroundRefresh()
        flightInfoFetchingSettingsRepository.setPeriodicFlightInfoFetchingEnabled(false)
    }

    actual suspend fun isFlightBackgroundSyncRunning(): Boolean =
        flightInfoFetchingSettingsRepository.isPeriodicFlightInfoFetchingEnabled.first()
}


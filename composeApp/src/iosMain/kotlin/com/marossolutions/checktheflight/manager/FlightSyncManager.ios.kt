package com.marossolutions.checktheflight.manager

import com.marossolutions.checktheflight.scheduler.BackgroundTaskScheduler
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

actual class FlightSyncManager(
    private val backgroundTaskScheduler: BackgroundTaskScheduler,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository
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

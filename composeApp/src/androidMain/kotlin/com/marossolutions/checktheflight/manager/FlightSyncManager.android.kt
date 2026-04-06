package com.marossolutions.checktheflight.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.marossolutions.checktheflight.workmanager.FlightInfoSyncWorker
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

private const val WORK_NAME = "flight_sync"

actual class FlightSyncManager(
    private val context: Context,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository
) {
    actual suspend fun startFlightBackgroundSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<FlightInfoSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            uniqueWorkName = WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = request
        )

        flightInfoFetchingSettingsRepository.setPeriodicFlightInfoFetchingEnabled(true)
    }

    actual suspend fun stopFlightBackgroundSync() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        flightInfoFetchingSettingsRepository.setPeriodicFlightInfoFetchingEnabled(false)
    }

    actual suspend fun isFlightBackgroundSyncRunning(): Boolean =
        flightInfoFetchingSettingsRepository.isPeriodicFlightInfoFetchingEnabled.first()
}

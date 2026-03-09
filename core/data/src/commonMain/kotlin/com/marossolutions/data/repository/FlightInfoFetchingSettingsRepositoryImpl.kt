package com.marossolutions.data.repository

import com.marossolutions.data.datastore.FlightInfoFetchingSettingsPreferences
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class FlightInfoFetchingSettingsRepositoryImpl(
    private val flightInfoFetchingSettingsPreferences: FlightInfoFetchingSettingsPreferences,
) : FlightInfoFetchingSettingsRepository {

    override val isPeriodicFlightInfoFetchingEnabled: Flow<Boolean> =
        flightInfoFetchingSettingsPreferences.isPeriodicFlightInfoFetchingEnabled

    override val lastBackgroundFetchTime: Flow<Instant?> =
        flightInfoFetchingSettingsPreferences.lastBackgroundFetchTime.map {
            it?.let { millis -> Instant.fromEpochMilliseconds(millis) }
        }
    override val lastSuccessfulBackgroundFetchTime: Flow<Instant?> =
        flightInfoFetchingSettingsPreferences.lastSuccessfulBackgroundFetchTime.map {
            it?.let { millis -> Instant.fromEpochMilliseconds(millis) }
        }

    override suspend fun setPeriodicFlightInfoFetchingEnabled(enabled: Boolean) {
        flightInfoFetchingSettingsPreferences.setPeriodicFlightInfoFetchingEnabled(enabled)
    }

    override suspend fun setLastBackgroundFetchTime(fetchTime: Instant) {
        flightInfoFetchingSettingsPreferences.setLastBackgroundFetchTime(
            fetchTime.toEpochMilliseconds()
        )
    }

    override suspend fun setLastSuccessfulBackgroundFetchTime(fetchTime: Instant) {
        flightInfoFetchingSettingsPreferences.setLastSuccessfulBackgroundFetchTime(
            fetchTime.toEpochMilliseconds()
        )
    }
}

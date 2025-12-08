package com.marossolutions.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface FlightInfoFetchingSettingsRepository {

    val isPeriodicFlightInfoFetchingEnabled: Flow<Boolean>

    val lastBackgroundFetchTime: Flow<Instant?>

    val lastSuccessfulBackgroundFetchTime: Flow<Instant?>

    suspend fun setPeriodicFlightInfoFetchingEnabled(enabled: Boolean)

    suspend fun setLastBackgroundFetchTime(fetchTime: Instant)

    suspend fun setLastSuccessfulBackgroundFetchTime(fetchTime: Instant)
}

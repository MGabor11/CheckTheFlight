package com.marossolutions.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface FlightInfoFetchingSettingsPreferences {
    val isPeriodicFlightInfoFetchingEnabled: Flow<Boolean>

    val lastBackgroundFetchTime: Flow<Long?>

    val lastSuccessfulBackgroundFetchTime: Flow<Long?>

    suspend fun setPeriodicFlightInfoFetchingEnabled(enabled: Boolean): Preferences

    suspend fun setLastBackgroundFetchTime(timeInMillis: Long): Preferences

    suspend fun setLastSuccessfulBackgroundFetchTime(timeInMillis: Long): Preferences
}

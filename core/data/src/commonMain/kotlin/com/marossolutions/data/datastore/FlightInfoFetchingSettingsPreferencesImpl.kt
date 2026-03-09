package com.marossolutions.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFS_TAG_KEY = "FlightInfoFetchingSettingsPreferences"
private const val IS_INFO_FETCHING_ENABLED = "IS_INFO_FETCHING_ENABLED"
private const val LAST_BACKGROUND_FETCH_TIME = "LAST_BACKGROUND_FETCH_TIME"
private const val LAST_SUCCESSFUL_BACKGROUND_FETCH_TIME = "LAST_SUCCESSFUL_BACKGROUND_FETCH_TIME"

internal class FlightInfoFetchingSettingsPreferencesImpl(private val dataStore: DataStore<Preferences>) :
    FlightInfoFetchingSettingsPreferences {

    private val isPeriodicFlightInfoFetchingEnabledKey =
        booleanPreferencesKey("$PREFS_TAG_KEY$IS_INFO_FETCHING_ENABLED")

    private val lastBackgroundFetchTimeKey =
        longPreferencesKey("$PREFS_TAG_KEY$LAST_BACKGROUND_FETCH_TIME")

    private val lastSuccessfulBackgroundFetchTimeKey =
        longPreferencesKey("$PREFS_TAG_KEY$LAST_SUCCESSFUL_BACKGROUND_FETCH_TIME")

    override val isPeriodicFlightInfoFetchingEnabled: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[isPeriodicFlightInfoFetchingEnabledKey] ?: false
        }

    override val lastBackgroundFetchTime: Flow<Long?> = dataStore.data.map { preferences ->
        preferences[lastBackgroundFetchTimeKey]
    }

    override val lastSuccessfulBackgroundFetchTime: Flow<Long?> =
        dataStore.data.map { preferences ->
            preferences[lastSuccessfulBackgroundFetchTimeKey]
        }

    override suspend fun setPeriodicFlightInfoFetchingEnabled(enabled: Boolean): Preferences =
        dataStore.edit { preferences ->
            preferences[isPeriodicFlightInfoFetchingEnabledKey] = enabled
        }

    override suspend fun setLastBackgroundFetchTime(timeInMillis: Long): Preferences =
        dataStore.edit { preferences ->
            preferences[lastBackgroundFetchTimeKey] = timeInMillis
        }

    override suspend fun setLastSuccessfulBackgroundFetchTime(timeInMillis: Long): Preferences =
        dataStore.edit { preferences ->
            preferences[lastSuccessfulBackgroundFetchTimeKey] = timeInMillis
        }
}

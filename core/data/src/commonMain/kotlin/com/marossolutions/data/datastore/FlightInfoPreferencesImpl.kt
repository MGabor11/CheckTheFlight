package com.marossolutions.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFS_TAG_KEY = "FlightInfoPreferences"
private const val FLIGHT_INFO = "FLIGHT_INFO"
private const val FLIGHT_NUMBER = "FLIGHT_NUMBER"

internal class FlightInfoPreferencesImpl(private val dataStore: DataStore<Preferences>) :
    FlightInfoPreferences {

    private val flightNumberKey = stringPreferencesKey("$PREFS_TAG_KEY$FLIGHT_NUMBER")
    private val flightInfoKey = stringPreferencesKey("$PREFS_TAG_KEY$FLIGHT_INFO")

    override suspend fun setFlightNumber(flightNumber: String): Preferences =
        dataStore.edit { preferences ->
            preferences[flightNumberKey] = flightNumber
        }

    override val flightNumber: Flow<String?> = dataStore.data.map { preferences ->
        preferences[flightNumberKey]
    }


    override val flightInfoJson: Flow<String?> = dataStore.data.map { preferences ->
        preferences[flightInfoKey]
    }

    override suspend fun setFlightInfoJson(flightInfoJson: String): Preferences =
        dataStore.edit { preferences ->
            preferences[flightInfoKey] = flightInfoJson
        }
}

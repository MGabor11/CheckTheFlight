package com.marossolutions.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface FlightInfoPreferences {
    val flightNumber: Flow<String?>

    val flightInfoJson: Flow<String?>

    suspend fun setFlightInfoJson(flightInfoJson: String): Preferences

    suspend fun setFlightNumber(flightNumber: String): Preferences
}

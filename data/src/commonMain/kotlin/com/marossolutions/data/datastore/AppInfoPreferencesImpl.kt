package com.marossolutions.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val PREFS_TAG_KEY = "AppInfoPreferences"
private const val IS_INFO_VIEWED = "IS_INFO_VIEWED"

internal class AppInfoPreferencesImpl(private val dataStore: DataStore<Preferences>) :
    AppInfoPreferences {

    private val infoViewedKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_INFO_VIEWED")

    override suspend fun isInfoViewed(): Boolean = dataStore.data.map { preferences ->
        preferences[infoViewedKey] ?: false
    }.first()

    override suspend fun setInfoViewed(isViewed: Boolean): Preferences = dataStore.edit { preferences ->
        preferences[infoViewedKey] = isViewed
    }
}
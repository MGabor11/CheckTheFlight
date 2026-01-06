package com.marossolutions.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val PREFS_TAG_KEY = "NotificationIdPreferences"
private const val NOTIFICATION_ID = "NOTIFICATION_ID"

internal class NotificationIdPreferencesImpl(private val dataStore: DataStore<Preferences>) :
    NotificationIdPreferences {

    private val notificationIdKey = intPreferencesKey("$PREFS_TAG_KEY$NOTIFICATION_ID")

    override suspend fun getNotificationId(): Int = dataStore.data.map { preferences ->
        preferences[notificationIdKey] ?: 1
    }.first()

    override suspend fun setNotificationId(id: Int): Preferences = dataStore.edit { preferences ->
        preferences[notificationIdKey] = id
    }
}

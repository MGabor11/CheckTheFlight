package com.marossolutions.data.datastore

import androidx.datastore.preferences.core.Preferences

interface NotificationIdPreferences {
    suspend fun getNotificationId(): Int

    suspend fun setNotificationId(id: Int): Preferences
}
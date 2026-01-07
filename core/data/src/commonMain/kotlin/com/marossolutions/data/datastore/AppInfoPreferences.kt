package com.marossolutions.data.datastore

import androidx.datastore.preferences.core.Preferences

interface AppInfoPreferences {
    suspend fun isInfoViewed(): Boolean

    suspend fun setInfoViewed(isViewed: Boolean): Preferences
}

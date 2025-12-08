package com.marossolutions.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.marossolutions.data.datastore.AppInfoPreferences
import com.marossolutions.data.datastore.AppInfoPreferencesImpl
import com.marossolutions.data.datastore.FlightInfoFetchingSettingsPreferences
import com.marossolutions.data.datastore.FlightInfoFetchingSettingsPreferencesImpl
import com.marossolutions.data.datastore.FlightInfoPreferences
import com.marossolutions.data.datastore.FlightInfoPreferencesImpl
import com.marossolutions.data.datastore.createDataStore
import com.marossolutions.data.di.qualifier.dataStorePath
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore { get<String>(qualifier = dataStorePath) } }
    singleOf(::AppInfoPreferencesImpl).bind<AppInfoPreferences>()
    singleOf(::FlightInfoPreferencesImpl).bind<FlightInfoPreferences>()
    singleOf(::FlightInfoFetchingSettingsPreferencesImpl).bind<FlightInfoFetchingSettingsPreferences>()
}

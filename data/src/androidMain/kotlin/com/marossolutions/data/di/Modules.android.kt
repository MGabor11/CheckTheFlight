package com.marossolutions.data.di

import com.marossolutions.data.datastore.NotificationIdPreferences
import com.marossolutions.data.datastore.NotificationIdPreferencesImpl
import com.marossolutions.data.datastore.dataStoreFileName
import com.marossolutions.data.di.qualifier.dataStorePath
import com.marossolutions.data.provider.NotificationIdProviderImpl
import com.marossolutions.domain.provider.NotificationIdProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single<String>(qualifier = dataStorePath) {
        androidContext().filesDir
            .resolve(dataStoreFileName)
            .absolutePath
    }
    singleOf(::NotificationIdPreferencesImpl).bind<NotificationIdPreferences>()
    singleOf(::NotificationIdProviderImpl).bind<NotificationIdProvider>()
}

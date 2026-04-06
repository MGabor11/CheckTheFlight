package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.notification.NotificationManager
import com.marossolutions.flightsync.FlightSyncNotifier
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::NotificationManager).bind<FlightSyncNotifier>()
}

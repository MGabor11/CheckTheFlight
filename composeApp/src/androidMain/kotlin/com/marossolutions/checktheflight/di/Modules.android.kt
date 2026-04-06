package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.manager.FlightSyncManager
import com.marossolutions.checktheflight.notification.NotificationManager
import com.marossolutions.checktheflight.workmanager.FlightInfoSyncWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::NotificationManager)
    singleOf(::FlightSyncManager)
    workerOf(::FlightInfoSyncWorker)
}

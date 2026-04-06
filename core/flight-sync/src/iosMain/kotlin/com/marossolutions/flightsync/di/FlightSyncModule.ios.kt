package com.marossolutions.flightsync.di

import com.marossolutions.flightsync.BackgroundTaskScheduler
import com.marossolutions.flightsync.BackgroundTaskSchedulerImpl
import com.marossolutions.flightsync.FlightInfoSyncManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::FlightInfoSyncManager)
    singleOf(::BackgroundTaskSchedulerImpl).bind<BackgroundTaskScheduler>()
}


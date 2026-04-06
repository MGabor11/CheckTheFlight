package com.marossolutions.flightsync.di

import com.marossolutions.flightsync.FlightInfoSyncManager
import com.marossolutions.flightsync.FlightInfoSyncWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::FlightInfoSyncManager)
    workerOf(::FlightInfoSyncWorker)
}


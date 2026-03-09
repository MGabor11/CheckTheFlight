package com.marossolutions.flightsync.di

import com.marossolutions.flightsync.FlightInfoFetchService
import com.marossolutions.flightsync.FlightInfoFetchServiceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val flightSyncModule = module {
    singleOf(::FlightInfoFetchServiceImpl).bind<FlightInfoFetchService>()
    includes(platformModule)
}


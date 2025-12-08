package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.service.FlightInfoFetchService
import com.marossolutions.checktheflight.service.FlightInfoFetchServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::FlightInfoFetchServiceImpl).bind<FlightInfoFetchService>()
}
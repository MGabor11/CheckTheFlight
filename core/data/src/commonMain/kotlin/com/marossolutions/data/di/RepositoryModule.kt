package com.marossolutions.data.di

import com.marossolutions.data.repository.AirlineRepositoryImpl
import com.marossolutions.data.repository.AirportRepositoryImpl
import com.marossolutions.data.repository.FlightInfoFetchingSettingsRepositoryImpl
import com.marossolutions.data.repository.FlightInfoRepositoryImpl
import com.marossolutions.domain.repository.AirlineRepository
import com.marossolutions.domain.repository.AirportRepository
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import com.marossolutions.domain.repository.FlightInfoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::AirportRepositoryImpl).bind<AirportRepository>()
    singleOf(::AirlineRepositoryImpl).bind<AirlineRepository>()
    singleOf(::FlightInfoRepositoryImpl).bind<FlightInfoRepository>()
    singleOf(::FlightInfoFetchingSettingsRepositoryImpl).bind<FlightInfoFetchingSettingsRepository>()
}

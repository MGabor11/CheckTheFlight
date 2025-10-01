package com.marossolutions.dibridge

import com.marossolutions.data.repository.AirlineRepositoryImpl
import com.marossolutions.data.repository.AirportRepositoryImpl
import com.marossolutions.domain.repository.AirlineRepository
import com.marossolutions.domain.repository.AirportRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::AirportRepositoryImpl).bind<AirportRepository>()
    singleOf(::AirlineRepositoryImpl).bind<AirlineRepository>()
}

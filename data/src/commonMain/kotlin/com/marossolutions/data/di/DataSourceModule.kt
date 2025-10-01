package com.marossolutions.data.di

import com.marossolutions.data.di.qualifier.apiNinjaBaseUrl
import com.marossolutions.data.network.AirlineRemoteService
import com.marossolutions.data.network.AirlineRemoteServiceImpl
import com.marossolutions.data.network.AirportRemoteService
import com.marossolutions.data.network.AirportRemoteServiceImpl
import com.marossolutions.data.service.AirlineService
import com.marossolutions.data.service.AirlineServiceImpl
import com.marossolutions.data.service.AirportService
import com.marossolutions.data.service.AirportServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val remoteDatasource = module {
    single<AirportRemoteService> {
        AirportRemoteServiceImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }
    single<AirlineRemoteService> {
        AirlineRemoteServiceImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }
    singleOf(::AirportServiceImpl).bind<AirportService>()
    singleOf(::AirlineServiceImpl).bind<AirlineService>()
}

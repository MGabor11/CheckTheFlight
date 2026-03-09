package com.marossolutions.data.di

import com.marossolutions.data.di.qualifier.airlabsBaseUrl
import com.marossolutions.data.di.qualifier.apiNinjaBaseUrl
import com.marossolutions.data.network.AirlineApi
import com.marossolutions.data.network.AirlineApiImpl
import com.marossolutions.data.network.AirportApi
import com.marossolutions.data.network.AirportApiImpl
import com.marossolutions.data.network.FlightInfoApi
import com.marossolutions.data.network.FlightInfoApiImpl
import org.koin.dsl.module

internal val apiModule = module {
    single<AirportApi> {
        AirportApiImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }
    single<AirlineApi> {
        AirlineApiImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }

    single<FlightInfoApi> {
        FlightInfoApiImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            airlabsBaseUrl = get<String>(qualifier = airlabsBaseUrl),
        )
    }
}
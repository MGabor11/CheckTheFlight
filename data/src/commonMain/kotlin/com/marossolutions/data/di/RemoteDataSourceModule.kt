package com.marossolutions.data.di

import com.marossolutions.data.service.AirlineRemoteDataSource
import com.marossolutions.data.service.AirlineRemoteDataSourceImpl
import com.marossolutions.data.service.AirportRemoteDataSource
import com.marossolutions.data.service.AirportRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val remoteDataSourceModule = module {
    singleOf(::AirportRemoteDataSourceImpl).bind<AirportRemoteDataSource>()
    singleOf(::AirlineRemoteDataSourceImpl).bind<AirlineRemoteDataSource>()
}

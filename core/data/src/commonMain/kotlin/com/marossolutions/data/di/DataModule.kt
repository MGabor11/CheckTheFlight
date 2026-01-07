package com.marossolutions.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    includes(
        apiKeyModule,
        httpClientModule,
        apiModule,
        remoteDataSourceModule,
        repositoryModule,
        dataStoreModule,
        platformModule,
    )
}

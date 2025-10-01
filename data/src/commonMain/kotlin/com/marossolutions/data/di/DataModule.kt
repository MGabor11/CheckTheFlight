package com.marossolutions.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(
        apiKeyModule,
        httpClientModule,
        remoteDatasource
    )
}
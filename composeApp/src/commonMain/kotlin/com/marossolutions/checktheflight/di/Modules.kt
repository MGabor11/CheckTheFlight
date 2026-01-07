package com.marossolutions.checktheflight.di

import com.marossolutions.airline.di.airlineModule
import com.marossolutions.airport.di.airportModule
import com.marossolutions.common.di.commonModule
import com.marossolutions.data.di.dataModule
import com.marossolutions.di.navigationModule
import com.marossolutions.domain.di.domainModule
import com.marossolutions.home.di.homeModule
import com.marossolutions.welcome.di.welcomeModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        commonModule,
        domainModule,
        dataModule,
        navigationModule,
        serviceModule,

        // Features
        welcomeModule,
        homeModule,
        airlineModule,
        airportModule
    )
}

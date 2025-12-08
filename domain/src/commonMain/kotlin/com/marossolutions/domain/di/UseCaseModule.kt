package com.marossolutions.domain.di

import com.marossolutions.domain.usecase.ObserveAirportsUseCase
import com.marossolutions.domain.usecase.RefreshAirportsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

 val useCaseModule = module {
    factoryOf(::ObserveAirportsUseCase)
    factoryOf(::RefreshAirportsUseCase)
}

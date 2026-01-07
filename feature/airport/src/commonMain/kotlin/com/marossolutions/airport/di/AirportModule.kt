package com.marossolutions.airport.di

import com.marossolutions.airport.viewmodel.AirportDetailViewModel
import com.marossolutions.airport.viewmodel.AirportsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val airportModule = module {
    viewModelOf(::AirportsViewModel)
    viewModelOf(::AirportDetailViewModel)
}
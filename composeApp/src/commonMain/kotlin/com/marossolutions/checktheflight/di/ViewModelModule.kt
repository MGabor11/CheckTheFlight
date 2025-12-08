package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.ui.airlinedetail.AirlineDetailViewModel
import com.marossolutions.checktheflight.ui.airlines.AirlinesViewModel
import com.marossolutions.checktheflight.ui.airportdetail.AirportDetailViewModel
import com.marossolutions.checktheflight.ui.airports.AirportsViewModel
import com.marossolutions.checktheflight.ui.home.HomeViewModel
import com.marossolutions.checktheflight.ui.welcome.WelcomeViewModel

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::AirportsViewModel)
    viewModelOf(::AirportDetailViewModel)
    viewModelOf(::AirlinesViewModel)
    viewModelOf(::AirlineDetailViewModel)
    viewModelOf(::HomeViewModel)
}
package com.marossolutions.airline.di

import com.marossolutions.airline.viewmodel.AirlineDetailViewModel
import com.marossolutions.airline.viewmodel.AirlinesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val airlineModule = module {
    viewModelOf(::AirlinesViewModel)
    viewModelOf(::AirlineDetailViewModel)
}


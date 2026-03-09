package com.marossolutions.airport.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.marossolutions.airport.ui.AirportDetailScreen
import com.marossolutions.airport.ui.AirportsScreen
import com.marossolutions.navigation.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.airportEntry() {
    entry<Route.ScreenAirports> {
        AirportsScreen()
    }
    entry<Route.ScreenAirportDetail> { entry ->
        AirportDetailScreen(
            viewModel = koinViewModel {
                parametersOf(entry.icao)
            }
        )
    }
}
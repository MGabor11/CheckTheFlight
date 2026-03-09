package com.marossolutions.airline.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.marossolutions.airline.ui.AirlineDetailScreen
import com.marossolutions.airline.ui.AirlinesScreen
import com.marossolutions.navigation.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.airlineEntry() {
    entry<Route.ScreenAirlines> {
        AirlinesScreen()
    }
    entry<Route.ScreenAirlineDetail> { entry ->
        AirlineDetailScreen(
            viewModel = koinViewModel {
                parametersOf(entry.icao)
            }
        )
    }
}

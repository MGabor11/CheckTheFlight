package com.marossolutions.checktheflight.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.marossolutions.checktheflight.ui.airlinedetail.AirlineDetailScreen
import com.marossolutions.checktheflight.ui.airlines.AirlinesScreen
import com.marossolutions.checktheflight.ui.airportdetail.AirportDetailScreen
import com.marossolutions.checktheflight.ui.airports.AirportsScreen
import com.marossolutions.checktheflight.ui.home.HomeScreen
import com.marossolutions.checktheflight.ui.welcome.WelcomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<AppScreen>.appEntries() {
    entry<AppScreen.ScreenWelcome> {
        WelcomeScreen()
    }
    entry<AppScreen.BottomNavScreen.ScreenHome> {
        HomeScreen()
    }
    entry<AppScreen.BottomNavScreen.ScreenAirports> {
        AirportsScreen()
    }
    entry<AppScreen.BottomNavScreen.ScreenAirlines> {
        AirlinesScreen()
    }
    entry<AppScreen.ScreenAirportDetail> { entry ->
        AirportDetailScreen(
            viewModel = koinViewModel {
                parametersOf(entry.icao)
            }
        )
    }
    entry<AppScreen.ScreenAirlineDetail> { entry ->
        AirlineDetailScreen(
            viewModel = koinViewModel {
                parametersOf(entry.icao)
            }
        )
    }
}

package com.marossolutions.checktheflight.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.marossolutions.checktheflight.ui.airlinedetail.AirlineDetailScreen
import com.marossolutions.checktheflight.ui.airlines.AirlinesScreen
import com.marossolutions.checktheflight.ui.airportdetail.AirportDetailScreen
import com.marossolutions.checktheflight.ui.airports.AirportsScreen
import com.marossolutions.checktheflight.ui.home.HomeScreen
import com.marossolutions.checktheflight.ui.welcome.WelcomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationRoot(
    simpleNavigator: SimpleNavigator,
    innerPadding: PaddingValues,
    topLevelBackStack: TopLevelBackStack<AppScreen>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        modifier = modifier,
        backStack = topLevelBackStack.backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { topLevelBackStack.removeLast() },
        entryProvider = entryProvider {
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
                    viewModel =
                        koinViewModel {
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
    )
}

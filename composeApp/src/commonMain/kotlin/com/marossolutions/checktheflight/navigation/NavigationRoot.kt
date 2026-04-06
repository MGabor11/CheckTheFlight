package com.marossolutions.checktheflight.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.marossolutions.airline.navigation.airlineEntry
import com.marossolutions.airport.navigation.airportEntry
import com.marossolutions.home.navigation.homeEntry
import com.marossolutions.navigation.NavigationState
import com.marossolutions.navigation.toEntries
import com.marossolutions.welcome.navigation.welcomeEntry

@Composable
fun NavigationRoot(
    navigationState: NavigationState,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {

    val entryProvider = entryProvider {
        welcomeEntry()
        homeEntry()
        airportEntry()
        airlineEntry()
    }

    NavDisplay(
        modifier = modifier,
        onBack = onBackPress,
        entries = navigationState.toEntries(entryProvider),
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
    )
}

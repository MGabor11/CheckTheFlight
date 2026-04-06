package com.marossolutions.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Airlines
import androidx.compose.material.icons.outlined.FlightClass
import androidx.compose.material.icons.outlined.LocalAirport
import androidx.compose.ui.graphics.vector.ImageVector
import checktheflight.core.navigation.generated.resources.Res
import checktheflight.core.navigation.generated.resources.nav_airlines
import checktheflight.core.navigation.generated.resources.nav_airports
import checktheflight.core.navigation.generated.resources.nav_tracker
import org.jetbrains.compose.resources.StringResource

data class BottomNavItem(
    val icon: ImageVector,
    val titleRes: StringResource,
)

val TOP_LEVEL_DESTINATIONS: Map<Route, BottomNavItem> = mapOf(
    Route.ScreenHome to BottomNavItem(
        icon = Icons.Outlined.FlightClass,
        titleRes = Res.string.nav_tracker,
    ),
    Route.ScreenAirports to BottomNavItem(
        icon = Icons.Outlined.LocalAirport,
        titleRes = Res.string.nav_airports,
    ),
    Route.ScreenAirlines to BottomNavItem(
        icon = Icons.Outlined.Airlines,
        titleRes = Res.string.nav_airlines,
    ),
)

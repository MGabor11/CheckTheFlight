package com.marossolutions.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Airlines
import androidx.compose.material.icons.outlined.FlightClass
import androidx.compose.material.icons.outlined.LocalAirport
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val title: String,
)

val TOP_LEVEL_DESTINATIONS: Map<Route, BottomNavItem> = mapOf(
    Route.ScreenHome to BottomNavItem(
        icon = Icons.Outlined.FlightClass,
        title = "Tracker"
    ),
    Route.ScreenAirports to BottomNavItem(
        icon = Icons.Outlined.LocalAirport,
        title = "Airports"
    ),
    Route.ScreenAirlines to BottomNavItem(
        icon = Icons.Outlined.Airlines,
        title = "Airlines"
    ),
)

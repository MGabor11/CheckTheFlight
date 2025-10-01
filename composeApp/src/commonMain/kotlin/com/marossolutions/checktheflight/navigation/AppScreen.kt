package com.marossolutions.checktheflight.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppScreen : NavKey {
    val title: String

    @Serializable
    object ScreenWelcome : AppScreen {
        override val title = "Welcome"
    }

    @Serializable
    data class ScreenAirportDetail(val icao: String) : AppScreen {
        override val title: String = "Airport Detail"
    }

    @Serializable
    data class ScreenAirlineDetail(val icao: String) : AppScreen {
        override val title: String = "Airline Detail"
    }

    sealed interface BottomNavScreen : AppScreen {

        val navIcon: ImageVector
        val navTitle: String

        @Serializable
        object ScreenHome : BottomNavScreen {
            override val navIcon: ImageVector = Icons.Filled.Home
            override val navTitle = "Home"
            override val title: String = "Home"
        }

        @Serializable
        object ScreenAirports : BottomNavScreen {
            override val navIcon: ImageVector = Icons.AutoMirrored.Filled.List
            override val navTitle = "Airports"
            override val title: String = "Airports"
        }

        @Serializable
        object ScreenAirlines : BottomNavScreen {
            override val navIcon: ImageVector = Icons.AutoMirrored.Filled.List
            override val navTitle = "Airlines"
            override val title: String = "Airlines"
        }
    }
}
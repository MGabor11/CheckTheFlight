package com.marossolutions.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    val title: String

    @Serializable
    object ScreenWelcome : Route {
        override val title = "Welcome"
    }

    @Serializable
    object ScreenTutorial : Route {
        override val title = "Tutorial"
    }

    @Serializable
    object ScreenHome : Route {
        override val title: String = "Home"
    }

    @Serializable
    object ScreenAirports : Route {
        override val title: String = "Airports"
    }

    @Serializable
    data class ScreenAirportDetail(val icao: String) : Route {
        override val title: String = "Airport Detail"
    }

    @Serializable
    object ScreenAirlines : Route {
        override val title: String = "Airlines"
    }

    @Serializable
    data class ScreenAirlineDetail(val icao: String) : Route {
        override val title: String = "Airline Detail"
    }
}

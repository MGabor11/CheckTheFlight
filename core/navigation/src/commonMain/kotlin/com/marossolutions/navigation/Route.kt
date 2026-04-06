package com.marossolutions.navigation

import androidx.navigation3.runtime.NavKey
import checktheflight.core.navigation.generated.resources.Res
import checktheflight.core.navigation.generated.resources.route_airline_detail
import checktheflight.core.navigation.generated.resources.route_airlines
import checktheflight.core.navigation.generated.resources.route_airport_detail
import checktheflight.core.navigation.generated.resources.route_airports
import checktheflight.core.navigation.generated.resources.route_home
import checktheflight.core.navigation.generated.resources.route_tutorial
import checktheflight.core.navigation.generated.resources.route_welcome
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.StringResource

@Serializable
sealed interface Route: NavKey {
    val titleRes: StringResource

    @Serializable
    object ScreenWelcome : Route {
        override val titleRes = Res.string.route_welcome
    }

    @Serializable
    object ScreenTutorial : Route {
        override val titleRes = Res.string.route_tutorial
    }

    @Serializable
    object ScreenHome : Route {
        override val titleRes = Res.string.route_home
    }

    @Serializable
    object ScreenAirports : Route {
        override val titleRes = Res.string.route_airports
    }

    @Serializable
    data class ScreenAirportDetail(val icao: String) : Route {
        @Transient
        override val titleRes = Res.string.route_airport_detail
    }

    @Serializable
    object ScreenAirlines : Route {
        override val titleRes = Res.string.route_airlines
    }

    @Serializable
    data class ScreenAirlineDetail(val icao: String) : Route {
        @Transient
        override val titleRes = Res.string.route_airline_detail
    }
}

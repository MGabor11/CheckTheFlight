package com.marossolutions.checktheflight.navigation

sealed interface NavigationEvent {

    data object NavigateUp : NavigationEvent

    data class ForwardNavigation(
        val screen: AppScreen,
        val navigationOptions: NavigationOptions? = null
    ) : NavigationEvent
}

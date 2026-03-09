package com.marossolutions.navigation

interface Navigator {
    fun setNavigationState(state: NavigationState)
    fun navigateTo(route: Route)
    fun navigateBack(): Boolean
    fun switchTopLevel(route: Route)
}


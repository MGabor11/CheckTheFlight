package com.marossolutions.checktheflight.navigation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SimpleNavigator {

    val navigationEvents: SharedFlow<NavigationEvent>

   /* val currentAppScreen: StateFlow<AppScreen?>

    fun setCurrentAppScreen(screen: AppScreen?)
*/
    fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions? = null)

    fun navigateUp()
}
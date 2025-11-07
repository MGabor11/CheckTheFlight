package com.marossolutions.checktheflight.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList

interface SimpleNavigator {

    val backStack: SnapshotStateList<AppScreen>

    fun getTopLevelScreen(): AppScreen

    fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions? = null)

    fun navigateUp()
}

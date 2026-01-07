package com.marossolutions.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.marossolutions.home.ui.HomeScreen
import com.marossolutions.navigation.Route

fun EntryProviderScope<NavKey>.homeEntry() {
    entry<Route.ScreenHome> {
        HomeScreen()
    }
}

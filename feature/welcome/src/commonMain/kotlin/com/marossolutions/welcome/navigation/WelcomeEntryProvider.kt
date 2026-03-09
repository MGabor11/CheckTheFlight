package com.marossolutions.welcome.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.marossolutions.navigation.Route
import com.marossolutions.welcome.ui.TutorialScreen
import com.marossolutions.welcome.ui.WelcomeScreen

fun EntryProviderScope<NavKey>.welcomeEntry() {
    entry<Route.ScreenWelcome> {
        WelcomeScreen()
    }
    entry<Route.ScreenTutorial> {
        TutorialScreen()
    }
}

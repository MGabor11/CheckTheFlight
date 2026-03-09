package com.marossolutions.welcome.viewmodel

import androidx.lifecycle.ViewModel
import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.Route

class TutorialViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    fun navigateToNextScreen() {
        navigator.navigateTo(Route.ScreenHome)
    }
}
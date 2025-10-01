package com.marossolutions.checktheflight.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.SimpleNavigator

class WelcomeViewModel(
    private val navigator: SimpleNavigator,
) : ViewModel() {

    fun navigateToNextScreen() {
        navigator.navigateTo(AppScreen.BottomNavScreen.ScreenHome)
    }
}
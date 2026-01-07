package com.marossolutions.welcome.di

import com.marossolutions.welcome.viewmodel.TutorialViewModel
import com.marossolutions.welcome.viewmodel.WelcomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val welcomeModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::TutorialViewModel)
}

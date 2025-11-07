package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.SimpleNavigator
import com.marossolutions.checktheflight.navigation.SimpleNavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::SimpleNavigatorImpl).bind<SimpleNavigator>()
}
package com.marossolutions.di

import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.NavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::NavigatorImpl).bind<Navigator>()
}
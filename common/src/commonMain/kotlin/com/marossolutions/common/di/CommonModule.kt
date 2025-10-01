package com.marossolutions.common.di

import com.marossolutions.common.dispatcher.DispatcherProvider
import com.marossolutions.common.dispatcher.DispatcherProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::DispatcherProviderImpl).bind<DispatcherProvider>()
}

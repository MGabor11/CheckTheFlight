package com.marossolutions.checktheflight.di

import com.marossolutions.dibridge.diBridgeModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        diBridgeModule,
        viewModelModule,
        navigationModule,
    )
}

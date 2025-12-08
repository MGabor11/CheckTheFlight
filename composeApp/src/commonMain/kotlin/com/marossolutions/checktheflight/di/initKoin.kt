package com.marossolutions.checktheflight.di

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin(config)
}

fun initAndGetKoin(config: KoinAppDeclaration? = null): Koin {
    val koinApp = startKoin(config)
    return koinApp.koin
}

private fun startKoin(config: KoinAppDeclaration?): KoinApplication = startKoin {
    config?.invoke(this)
    modules(sharedModule, platformModule)
}

package com.marossolutions.checktheflight

import com.marossolutions.checktheflight.di.platformModule
import com.marossolutions.checktheflight.di.sharedModule
import com.marossolutions.checktheflight.scheduler.BackgroundTaskScheduler
import org.koin.dsl.KoinAppDeclaration

/*
fun initKoinAndGetBackgroundTaskScheduler(config: KoinAppDeclaration? = null): BackgroundTaskScheduler {
    val koinApp = org.koin.core.context.startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }

    return koinApp.koin.get(BackgroundTaskScheduler::class)
}*/

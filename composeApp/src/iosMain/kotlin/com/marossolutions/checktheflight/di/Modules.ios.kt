package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.manager.FlightSyncManager
import com.marossolutions.checktheflight.notification.NotificationManager
import com.marossolutions.checktheflight.scheduler.BackgroundTaskScheduler
import com.marossolutions.checktheflight.scheduler.BackgroundTaskSchedulerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::NotificationManager)
    singleOf(::FlightSyncManager)
    singleOf(::BackgroundTaskSchedulerImpl).bind<BackgroundTaskScheduler>()
}

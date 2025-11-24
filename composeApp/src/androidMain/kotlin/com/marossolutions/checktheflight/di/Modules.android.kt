package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.notification.NotificationManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    //single { NotificationManager(context = get()) }
    singleOf(::NotificationManager)
}

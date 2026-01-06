package com.marossolutions.checktheflight

import android.app.Application
import androidx.work.Configuration
import com.marossolutions.checktheflight.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.factory.KoinWorkerFactory

class MyApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }

    override val workManagerConfiguration: Configuration = Configuration.Builder()
        .setWorkerFactory(KoinWorkerFactory())
        .build()
}

package com.marossolutions.checktheflight.di

import com.marossolutions.checktheflight.notification.NotificationManager
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val platformModule = module {
    /*singleOf(::IOSApplicationCloseService).bind<ApplicationCloseService>()

    single<String>(qualifier = dataStorePath) {
        documentDirectoryPath() + "/$dataStoreFileName"
    }
    single<RoomDatabase.Builder<AppDatabase>>(qualifier = nativeDataBaseBuilder) {
        val dbFilePath = documentDirectoryPath() + "/$dataBaseFileName"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }*/
    singleOf(::NotificationManager)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): NSURL? = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
)

private fun documentDirectoryPath() = requireNotNull(documentDirectory()).path

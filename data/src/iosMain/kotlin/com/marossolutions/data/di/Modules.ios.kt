package com.marossolutions.data.di

import com.marossolutions.data.datastore.dataStoreFileName
import com.marossolutions.data.di.qualifier.dataStorePath
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val platformModule = module {
    single<String>(qualifier = dataStorePath) {
        documentDirectoryPath() + "/$dataStoreFileName"
    }
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
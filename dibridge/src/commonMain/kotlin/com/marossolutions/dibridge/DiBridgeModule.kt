package com.marossolutions.dibridge

import com.marossolutions.common.di.commonModule
import com.marossolutions.data.di.dataModule
import org.koin.dsl.module

val diBridgeModule = module {
    includes(
        commonModule,
        dataModule,
        repositoryModule
    )
}

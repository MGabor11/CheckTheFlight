package com.marossolutions.data.di

import com.marossolutions.checktheflight.BuildKonfig
import com.marossolutions.data.di.qualifier.airlabsApiKey
import com.marossolutions.data.di.qualifier.airlabsBaseUrl
import com.marossolutions.data.di.qualifier.apiNinjaApiKey
import com.marossolutions.data.di.qualifier.apiNinjaBaseUrl
import com.marossolutions.data.di.qualifier.geoapifyMapsKey
import org.koin.dsl.module

internal val apiKeyModule = module {
    single<String>(qualifier = apiNinjaApiKey) { BuildKonfig.API_NINJA_API_KEY }
    single<String>(qualifier = airlabsApiKey) { BuildKonfig.AIRLABS_API_KEY }
    single<String>(qualifier = geoapifyMapsKey) { BuildKonfig.GEOAPIFY_MAPS_KEY }
    single<String>(qualifier = apiNinjaBaseUrl) { BuildKonfig.API_NINJA_BASE_URL }
    single<String>(qualifier = airlabsBaseUrl) { BuildKonfig.AIRLABS_BASE_URL }
}
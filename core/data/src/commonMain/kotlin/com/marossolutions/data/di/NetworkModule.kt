package com.marossolutions.data.di

import com.marossolutions.data.di.qualifier.airlabsApiKey
import com.marossolutions.data.di.qualifier.airlabsBaseUrl
import com.marossolutions.data.di.qualifier.apiNinjaApiKey
import com.marossolutions.data.di.qualifier.apiNinjaBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val httpClientModule = module {
    single {
        val apiNinjaApiKey: String = get(qualifier = apiNinjaApiKey)
        val apiNinjaBaseUrl: String = get(qualifier = apiNinjaBaseUrl)
        val airlabsApiKey: String = get(qualifier = airlabsApiKey)
        val airlabsBaseUrl: String = get(qualifier = airlabsBaseUrl)
        HttpClient {
            install(plugin = ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    },
                    contentType = ContentType.Any
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
        }.apply {
            plugin(HttpSend).intercept { request ->
                val url = request.url.toString()
                when {
                    url.startsWith(apiNinjaBaseUrl) -> request.headers.append("X-Api-Key", apiNinjaApiKey)
                    url.startsWith(airlabsBaseUrl) -> request.parameter("api_key", airlabsApiKey)
                    else -> error("Unknown base url: $url")
                }
                execute(request)
            }
        }
    }
}

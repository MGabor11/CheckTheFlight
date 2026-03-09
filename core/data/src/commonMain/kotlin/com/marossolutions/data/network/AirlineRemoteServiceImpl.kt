package com.marossolutions.data.network

import com.marossolutions.common.dispatcher.DispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext

internal class AirlineApiImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val apiNinjaBaseUrl: String,
) : AirlineApi {

    override suspend fun getAirline(icao: String): AirlineResponse? = withContext(dispatcherProvider.io) {
        val response = httpClient.get(apiNinjaBaseUrl + "airlines?icao=" + icao).body<List<AirlineResponse>>()
        return@withContext response.firstOrNull()
    }
}
package com.marossolutions.checktheflight.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.marossolutions.checktheflight.service.FlightInfoFetchService

class FlightInfoSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val flightInfoFetchService: FlightInfoFetchService,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("FlightInfoSyncWorker", "Worker started")
        flightInfoFetchService.fetchFlightInfo(withNotification = true)
        return Result.success()
    }
}

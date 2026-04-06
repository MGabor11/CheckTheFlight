package com.marossolutions.flightsync

import com.marossolutions.common.dispatcher.DispatcherProvider
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import platform.BackgroundTasks.BGAppRefreshTask
import platform.BackgroundTasks.BGAppRefreshTaskRequest
import platform.BackgroundTasks.BGTaskScheduler
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSinceNow

private const val TASK_ID = "com.marossolutions.checktheflight.backgroundRefresh"

class BackgroundTaskSchedulerImpl(
    dispatcherProvider: DispatcherProvider,
    private val flightInfoFetchService: FlightInfoFetchService,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository,
) : BackgroundTaskScheduler {

    private val scope = CoroutineScope(dispatcherProvider.default)

    override fun stopBackgroundRefresh() {
        BGTaskScheduler.sharedScheduler.cancelTaskRequestWithIdentifier(TASK_ID)
    }

    override fun registerBackgroundTasks() {
        BGTaskScheduler.sharedScheduler.registerForTaskWithIdentifier(
            identifier = TASK_ID, usingQueue = null
        ) { task ->
            handleBackgroundRefresh(task as BGAppRefreshTask)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun scheduleBackgroundRefresh() {
        val request = BGAppRefreshTaskRequest(identifier = TASK_ID)
        request.earliestBeginDate = NSDate.dateWithTimeIntervalSinceNow(15 * 60.0)

        try {
            BGTaskScheduler.sharedScheduler.submitTaskRequest(request, null)
            println("BGAppRefresh scheduled")
        } catch (e: Throwable) {
            println("Failed to schedule BGAppRefresh: ${e.message}")
        }
    }

    override fun reScheduleBackgroundRefreshIfNeeded() {
        scope.launch {
            if (flightInfoFetchingSettingsRepository.isPeriodicFlightInfoFetchingEnabled.firstOrNull() == true) {
                scheduleBackgroundRefresh()
            }
        }
    }

    private fun handleBackgroundRefresh(task: BGAppRefreshTask) {
        scope.launch {
            if (flightInfoFetchingSettingsRepository.isPeriodicFlightInfoFetchingEnabled.firstOrNull() == true) {
                scheduleBackgroundRefresh() // rescheduling
            }

            task.expirationHandler = {
                // cleanup
                scope.cancel()
                task.setTaskCompletedWithSuccess(false)
            }

            try {
                flightInfoFetchService.fetchFlightInfo(withNotification = true)
                task.setTaskCompletedWithSuccess(true)
            } catch (e: Throwable) {
                task.setTaskCompletedWithSuccess(false)
            }
        }
    }
}


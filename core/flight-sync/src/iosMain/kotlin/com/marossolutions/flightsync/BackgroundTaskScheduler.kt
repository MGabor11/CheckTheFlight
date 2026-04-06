package com.marossolutions.flightsync

interface BackgroundTaskScheduler {

    fun stopBackgroundRefresh()

    fun registerBackgroundTasks()

    fun scheduleBackgroundRefresh()

    fun reScheduleBackgroundRefreshIfNeeded()
}


package com.marossolutions.checktheflight.scheduler

interface BackgroundTaskScheduler {

    fun stopBackgroundRefresh()

    fun registerBackgroundTasks()

    fun scheduleBackgroundRefresh()

    fun reScheduleBackgroundRefreshIfNeeded()
}
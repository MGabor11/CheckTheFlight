package com.marossolutions.checktheflight.notification

expect class NotificationManager {

    fun showNotification(
        title: String,
        description: String
    )
}
package com.marossolutions.domain.provider

interface NotificationIdProvider {

   suspend fun getNotificationId(): Int
}
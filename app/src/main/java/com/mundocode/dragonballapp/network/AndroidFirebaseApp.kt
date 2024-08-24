package com.mundocode.dragonballapp.network

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class AndroidFirebaseApp : Application() {
    companion object {
        const val FCM_CHANNEL_ID = "FCM_CHANNEL_ID"
    }

    override fun onCreate() {
        super.onCreate()
        val fcmChannel = NotificationChannel(FCM_CHANNEL_ID, "FCM_Channel", NotificationManager.IMPORTANCE_HIGH)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(fcmChannel)
    }
}
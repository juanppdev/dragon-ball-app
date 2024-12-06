package com.mundocode.dragonballapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DragonBallApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val fcmChannel = NotificationChannel(
            FCM_CHANNEL_ID,
            FCM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).run {
            createNotificationChannel(fcmChannel)
        }
    }

    companion object {
        const val FCM_CHANNEL_NAME = "FCM_Channel"
        const val FCM_CHANNEL_ID = "FCM_CHANNEL_ID"
    }
}
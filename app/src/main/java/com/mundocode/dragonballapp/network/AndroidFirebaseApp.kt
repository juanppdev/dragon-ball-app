package com.mundocode.dragonballapp.network

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidFirebaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val fcmChannel = NotificationChannel(FCM_CHANNEL_ID, "FCM_Channel", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(fcmChannel)
        }
    }

    companion object {
        const val FCM_CHANNEL_ID = "FCM_CHANNEL_ID"
    }
}
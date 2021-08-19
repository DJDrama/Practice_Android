package com.example.android.eggtimernotifications

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.eggtimernotifications.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    private val TAG="MyFirebase"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived: ${remoteMessage?.from}")

        // data (only when app is on foreground)
        remoteMessage?.data?.let{
            Log.d(TAG, "Msg data payload: " + remoteMessage.data)
        }

        // notification (foreground and background)
        remoteMessage?.notification?.let{
            Log.d(TAG, "Msg Notification Body: " + it.body)
            sendNotification(it.body!!)
        }

    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")

        //sendRegistrationToServer(token)
    }

    private fun sendNotification(messageBody: String){
        val nm = ContextCompat.getSystemService(applicationContext,
        NotificationManager::class.java) as NotificationManager
        nm.sendNotification(messageBody, applicationContext)
    }
}
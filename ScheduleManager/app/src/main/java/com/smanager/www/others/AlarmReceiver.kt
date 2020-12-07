package com.smanager.www.others

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.smanager.www.R
import com.smanager.www.others.Constants.NOTIFICATION_CHANNEL_ID
import com.smanager.www.others.Constants.NOTIFICATION_CHANNEL_NAME
import com.smanager.www.repository.CalendarRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var calendarRepository: CalendarRepository

    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let {
            val id = it.getLongExtra("id", -1).toInt()
            if (id > -1) {
                GlobalScope.launch(IO) {
                    val event = calendarRepository.getEventById(id)
                    p0?.let { c ->
                        val notificationManager =
                            c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            createNotificationChannel(notificationManager)
                        }

                        val builder = NotificationCompat.Builder(c, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(event.text)
                            .setContentText("${event.hour}:${event.minute}")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        notificationManager.notify(event.id!!, builder.build())
                    }
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}
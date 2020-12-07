package com.smanager.www.others

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.smanager.www.repository.CalendarRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var calendarRepository: CalendarRepository

    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let {
            val id = it.getLongExtra("id", -1).toInt()
            if (id > -1) {
                GlobalScope.launch(IO) {
                    val event = calendarRepository.getEventById(id)

                }
            }
        }

    }
}
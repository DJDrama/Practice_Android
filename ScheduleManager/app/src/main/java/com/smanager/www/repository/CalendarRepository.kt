package com.smanager.www.repository

import com.smanager.www.db.EventDao
import com.smanager.www.model.Event
import java.time.LocalDate
import javax.inject.Inject

class CalendarRepository @Inject
constructor(private val eventDao: EventDao) {

    suspend fun insertEvent(event: Event) = eventDao.insertEvent(event)
    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)

    suspend fun getEventById(id: Int) = eventDao.getEventById(id)

    suspend fun getEventsByDate(localDate: LocalDate) = eventDao.getAllEventsByDate(localDate)
    suspend fun getAllEvents() = eventDao.getAllEvents()

}
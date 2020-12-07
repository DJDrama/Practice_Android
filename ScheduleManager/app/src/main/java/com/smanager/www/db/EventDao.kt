package com.smanager.www.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.smanager.www.model.Event
import java.time.LocalDate

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event): Long

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM event_table WHERE id=:id")
    suspend fun getEventById(id: Int): Event

    @Query("SELECT * FROM event_table WHERE localDate = :localDate")
    suspend fun getAllEventsByDate(localDate: LocalDate): List<Event>

    @Query("SELECT * FROM event_table")
    suspend fun getAllEvents(): List<Event>

}
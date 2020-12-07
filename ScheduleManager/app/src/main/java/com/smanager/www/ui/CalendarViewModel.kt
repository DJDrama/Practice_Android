package com.smanager.www.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smanager.www.model.Event
import com.smanager.www.repository.CalendarRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel
@ViewModelInject
constructor(
    val calendarRepository: CalendarRepository
) : ViewModel() {

    private var _eventListMutableLiveData = MutableLiveData<List<Event>>()
    val eventListLiveData: LiveData<List<Event>>
        get() = _eventListMutableLiveData

    private var _allEvents = MutableLiveData<HashMap<LocalDate, List<Event>>>()
    val allEvents: LiveData<HashMap<LocalDate, List<Event>>>
        get() = _allEvents

    private var _currEvent = MutableLiveData<Event>()

    private var hour: Int? = null
    private var minute: Int? = null

    private var _insertedId = MutableLiveData<Long>()
    val insertedId: LiveData<Long>
        get() = _insertedId

    init {
        getAllEvents()
    }

    fun getAllEvents() {
        viewModelScope.launch {
            val list = calendarRepository.getAllEvents()
            val map = mutableMapOf<LocalDate, MutableList<Event>>()
            list.forEach { e ->
                if (map.containsKey(e.localDate)) {
                    map[e.localDate]?.add(e)
                } else {
                    map[e.localDate] = mutableListOf()
                }
            }
        }
    }

    fun getHour(): Int? = hour
    fun getMinute(): Int? = minute

    fun setCurrentEvent(event: Event){
        _currEvent.value = event
    }
    fun getCurrentEvent() = _currEvent.value
    fun addEvent() {
        val event = _currEvent.value
        event?.let {e->
            hour?.let { h ->
                e.hour = h
                minute?.let { m ->
                    e.minute = m
                    viewModelScope.launch {
                        _insertedId.value = calendarRepository.insertEvent(event)
                        getAllEventsByDate(event.localDate)
                    }
                }
            }
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            calendarRepository.deleteEvent(event)
            getAllEventsByDate(event.localDate)
        }
    }

    fun getAllEventsByDate(localDate: LocalDate) {
        viewModelScope.launch {
            val eventList = calendarRepository.getEventsByDate(localDate)
            _eventListMutableLiveData.postValue(eventList)
        }
    }


    fun setTime(hourOfDay: Int, minute: Int) {
        hour = hourOfDay
        this.minute = minute
    }


}
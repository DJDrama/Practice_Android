package com.smanager.www.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "event_table")
data class Event(
    val text: String,
    var localDate: LocalDate,
    var hour: Int?=null,
    var minute: Int?=null
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

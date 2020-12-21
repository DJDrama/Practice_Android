package com.introducemyselfsample.www.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="idol")
data class Idol(
    val name: String?=null,
    val number: Int=0
) {

    @PrimaryKey(autoGenerate = true)
    var idx: Int? = null
}
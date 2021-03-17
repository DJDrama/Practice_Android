package com.movierecom.www.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_keywords")
data class SearchKeyword(
    @PrimaryKey
    @ColumnInfo(name = "search_keyword")
    val searchKeyword: String,
    @ColumnInfo(name = "count")
    val count: Int=1
)
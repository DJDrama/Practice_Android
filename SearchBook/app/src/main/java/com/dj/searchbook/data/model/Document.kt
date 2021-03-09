package com.dj.searchbook.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "documents")
data class Document(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "contents")
    val contents: String,
    @ColumnInfo(name = "url")
    val url: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "isbn")
    val isbn: String,
    @ColumnInfo(name = "datetime")
    @Json(name = "datetime")
    val dateTime: Date,
    @ColumnInfo(name = "authors")
    val authors: List<String>,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "translators")
    val translators: List<String>,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "sale_price")
    @Json(name = "sale_price")
    val salePrice: Int,
    @ColumnInfo(name = "thumbnail")
    @Json(name = "thumbnail")
    val thumbNail: String,
    @ColumnInfo(name = "status")
    val status: String,
    var isFavorite: Boolean=false
) : Parcelable

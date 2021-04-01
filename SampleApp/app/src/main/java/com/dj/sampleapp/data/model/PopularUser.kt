package com.dj.sampleapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularUser(
    val nickname: String,
    val introduction: String,
    val id: Int
) : Parcelable

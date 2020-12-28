package com.introducemyselfsample.www.data

import com.introducemyselfsample.www.R

fun getAlbums(): List<Album> {
    val list = mutableListOf<Album>()
    list.add(Album("아저씨", R.drawable.a))
    list.add(Album("모나리자", R.drawable.b))
    list.add(Album("아저씨", R.drawable.a))
    list.add(Album("아저씨", R.drawable.a))
    list.add(Album("모나리자", R.drawable.b))
    list.add(Album("모나리자", R.drawable.b))
    list.add(Album("모나리자", R.drawable.b))
    list.add(Album("아저씨", R.drawable.a))
    list.add(Album("모나리자", R.drawable.b))
    list.add(Album("모나리자", R.drawable.b))
    return list
}

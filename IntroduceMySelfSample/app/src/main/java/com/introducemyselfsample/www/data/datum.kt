package com.introducemyselfsample.www.data

import com.introducemyselfsample.www.R

fun getAlbums(): List<Album> {
    val list = mutableListOf<Album>()
    list.add(Album("asdf", R.drawable.a))
    list.add(Album("bbbbbbb", R.drawable.b))
    list.add(Album("cccccc", R.drawable.b))
    list.add(Album("ddd", R.drawable.b))
    list.add(Album("ee", R.drawable.b))
    list.add(Album("f", R.drawable.b))
    list.add(Album("ggg", R.drawable.b))
    list.add(Album("hh", R.drawable.b))
    list.add(Album("u", R.drawable.b))
    list.add(Album("jjdsaf", R.drawable.b))
    return list
}

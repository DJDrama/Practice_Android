package com.dj.testing.repositories

import androidx.lifecycle.LiveData
import com.dj.testing.data.local.ShoppingItem
import com.dj.testing.data.remote.responses.ImageResponse
import com.dj.testing.other.Resource
import retrofit2.Response

interface ShoppingRepository{
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}
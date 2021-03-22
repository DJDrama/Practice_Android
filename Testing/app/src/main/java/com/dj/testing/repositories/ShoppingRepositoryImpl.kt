package com.dj.testing.repositories

import androidx.lifecycle.LiveData
import com.dj.testing.data.local.ShoppingDao
import com.dj.testing.data.local.ShoppingItem
import com.dj.testing.data.remote.PixabayAPI
import com.dj.testing.data.remote.responses.ImageResponse
import com.dj.testing.other.Resource
import retrofit2.Response
import javax.inject.Inject

class ShoppingRepositoryImpl
@Inject
constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try{
            val response = pixabayAPI.searchForImage(searchQuery = imageQuery)
            if(response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                } ?: Resource.error("Unkown Error", null)
            }else{
                Resource.error("Unkown Error", null)
            }
        }catch(e: Exception){
            Resource.error(e.message?:"Unkown Error", null)
        }
    }

}
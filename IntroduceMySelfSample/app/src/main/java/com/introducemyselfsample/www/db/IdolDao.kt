package com.introducemyselfsample.www.db

import androidx.room.*
import com.introducemyselfsample.www.data.Idol

@Dao
interface IdolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdol(idol: Idol)

    @Delete
    suspend fun deleteIdol(idol: Idol)

    @Update
    suspend fun update(idol: Idol)

    @Query("SELECT * FROM idol ORDER BY idx ASC")
    suspend fun getAllIdols(): List<Idol>


}
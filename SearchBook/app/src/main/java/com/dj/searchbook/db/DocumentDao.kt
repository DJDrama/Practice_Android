package com.dj.searchbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.util.PAGINATION_SIZE

@Dao
interface DocumentDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertDocuments(documents: List<Document>): LongArray

    @Query(
        """SELECT * FROM documents 
        WHERE title LIKE '%' || :query || '%' 
        ORDER BY datetime DESC
        LIMIT :pageSize OFFSET ((:page-1)*:pageSize)"""
    )
    suspend fun searchDocuments(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_SIZE
    ): List<Document>
}
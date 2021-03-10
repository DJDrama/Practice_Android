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

    // Removed ORDER BY datetime DESC
    @Query(
        """SELECT * FROM documents 
        WHERE title LIKE '%' || :query || '%'
        LIMIT :pageSize OFFSET ((:page-1)*:pageSize)"""
    )
    suspend fun searchDocuments(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_SIZE,
    ): List<Document>

    // Removed ORDER BY datetime DESC
    @Query(
        """SELECT * FROM documents
        WHERE title LIKE '%' || :query || '%' 
        LIMIT (:page*:pageSize)"""
    )
    suspend fun restoreDocuments(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_SIZE,
    ): List<Document>
}

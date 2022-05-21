package com.example.composenews.data.local.remotemediator

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PagingNewsDao {

    @Query("SELECT * FROM paging_news")
    fun getAllNews(): PagingSource<Int, PagingNewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news: List<PagingNewsEntity>)

    @Query("DELETE FROM paging_news")
    suspend fun deleteAll()
}
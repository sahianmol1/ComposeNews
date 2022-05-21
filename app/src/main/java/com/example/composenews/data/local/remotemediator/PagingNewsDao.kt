package com.example.composenews.data.local.remotemediator

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composenews.data.local.TopNewsEntity

@Dao
interface PagingNewsDao {

    @Query("SELECT * FROM top_news")
    fun getAllNews(): PagingSource<Int, TopNewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news: List<TopNewsEntity>)

    @Query("DELETE FROM top_news")
    suspend fun deleteAll()
}
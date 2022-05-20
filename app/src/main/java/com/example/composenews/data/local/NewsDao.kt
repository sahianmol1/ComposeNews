package com.example.composenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {

    @Query("SELECT * FROM top_news")
    suspend fun getAllNews(): List<TopNewsEntity>?

    @Insert
    suspend fun insertNews(news: TopNewsEntity)

    @Query("DELETE FROM top_news")
    suspend fun deleteAllNews()
}
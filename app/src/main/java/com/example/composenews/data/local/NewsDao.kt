package com.example.composenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM top_news")
    suspend fun getAllNews(): List<TopNewsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: TopNewsEntity)

    @Query("DELETE FROM top_news")
    suspend fun deleteAllNews()
}
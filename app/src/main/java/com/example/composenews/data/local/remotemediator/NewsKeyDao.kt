package com.example.composenews.data.local.remotemediator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsKeyDao {

    @Query("SELECT * FROM news_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: String): NewsKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(keys: List<NewsKey>)

    @Query("DELETE FROM news_remote_keys")
    suspend fun deleteAllKeys()

}
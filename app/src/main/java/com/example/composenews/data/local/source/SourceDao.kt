package com.example.composenews.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.composenews.data.remote.models.SourceItem

@Dao
interface SourceDao {

    @Insert
    suspend fun insertSource(source: SourceItem)

    @Query("SELECT * FROM source_table")
    suspend fun getAllSource(): List<SourceItem>?

    @Query("DELETE FROM source_table")
    suspend fun deleteAllSource()
}
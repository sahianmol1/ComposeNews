package com.example.composenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composenews.data.local.source.SourceDao
import com.example.composenews.data.remote.models.SourceItem

@Database(entities = [TopNewsEntity::class, SourceItem::class], version = 2)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun sourceDao(): SourceDao
}
package com.example.composenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composenews.data.local.remotemediator.NewsKey
import com.example.composenews.data.local.remotemediator.NewsKeyDao
import com.example.composenews.data.local.remotemediator.PagingNewsDao
import com.example.composenews.data.local.remotemediator.PagingNewsEntity
import com.example.composenews.data.local.source.SourceDao
import com.example.composenews.data.remote.models.SourceItem

@Database(entities = [TopNewsEntity::class, SourceItem::class, NewsKey::class, PagingNewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun sourceDao(): SourceDao
    abstract fun newsKeyDao(): NewsKeyDao
    abstract fun pagingNewsDao(): PagingNewsDao
}
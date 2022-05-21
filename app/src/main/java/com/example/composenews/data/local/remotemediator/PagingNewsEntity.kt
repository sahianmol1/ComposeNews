package com.example.composenews.data.local.remotemediator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paging_news")
data class PagingNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val url: String,
    val urlToImage: String? = "",
    val title: String,
    val description: String,
) {
}

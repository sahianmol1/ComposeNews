package com.example.composenews.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_news")
data class TopNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val url: String,
    val urlToImage: String? = "",
    val title: String,
    val description: String,
)

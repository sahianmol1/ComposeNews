package com.example.composenews.data.remote.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class SourceItem(
    val category: String,
    val country: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val language: String,
    val name: String,
    val url: String
)
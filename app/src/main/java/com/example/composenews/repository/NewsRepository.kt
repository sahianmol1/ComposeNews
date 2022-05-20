package com.example.composenews.repository

import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.utils.DataType

interface NewsRepository {

    suspend fun getNews(isRefresh: Boolean): List<TopNewsEntity>

    suspend fun getSources(isRefresh: Boolean): List<SourceItem>
}
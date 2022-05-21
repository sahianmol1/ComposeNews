package com.example.composenews.repository

import androidx.paging.PagingData
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.data.local.remotemediator.PagingNewsEntity
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.utils.SortBy
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(isRefresh: Boolean): List<TopNewsEntity>

    suspend fun getSources(isRefresh: Boolean): List<SourceItem>

    fun getEverything(query: String, sortBy: SortBy): Flow<PagingData<PagingNewsEntity>>
}
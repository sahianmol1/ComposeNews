package com.example.composenews.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.composenews.data.local.NewsDatabase
import com.example.composenews.data.local.remotemediator.NewsKey
import com.example.composenews.data.local.remotemediator.NewsKeyDao
import com.example.composenews.data.local.remotemediator.PagingNewsDao
import com.example.composenews.data.local.remotemediator.PagingNewsEntity
import com.example.composenews.data.remote.api.NewsApi
import com.example.composenews.utils.Constants.PAGE_SIZE
import com.example.composenews.utils.SortBy

@ExperimentalPagingApi
class NewsRemoteMediator constructor(
    private val pagingNewsDao: PagingNewsDao,
    private val newsKeyDao: NewsKeyDao,
    private val db: NewsDatabase,
    private val api: NewsApi,
    private val query: String,
    private val sortBy: SortBy,
) : RemoteMediator<Int, PagingNewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PagingNewsEntity>
    ): MediatorResult {
        return try {

            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = api.getEverything(page = currentPage, pageSize = PAGE_SIZE, query = query, sortBy = sortBy.name)
            val articles = response.body()?.articles
            val endOfPaginationReached = articles?.isEmpty() ?: true

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsKeyDao.deleteAllKeys()
                    pagingNewsDao.deleteAll()
                }

                val keys = articles?.map { article ->
                    NewsKey(
                        id = article.url,
                        prevKey = prevPage,
                        nextKey = nextPage,
                    )
                }

                newsKeyDao.insertAllKeys(keys ?: emptyList())

                val allNews = articles?.map { articles ->
                    PagingNewsEntity(
                        url = articles.url,
                        urlToImage = articles.urlToImage,
                        title = articles.title,
                        description = articles.description ?: "",
                        id = null
                    )
                }
                pagingNewsDao.insertAllNews(allNews ?: emptyList())
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PagingNewsEntity>
    ): NewsKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                newsKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PagingNewsEntity>
    ): NewsKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { news ->
                newsKeyDao.getRemoteKeys(id = news.url)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PagingNewsEntity>
    ): NewsKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { news ->
                newsKeyDao.getRemoteKeys(id = news.url)
            }
    }
}
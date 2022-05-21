package com.example.composenews.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composenews.data.local.NewsDao
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.data.local.remotemediator.PagingNewsDao
import com.example.composenews.data.local.source.SourceDao
import com.example.composenews.data.paging.NewsRemoteMediator
import com.example.composenews.data.remote.api.NewsApi
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val newsDao: NewsDao,
    private val sourceDao: SourceDao,
    private val pagingNewsDao: PagingNewsDao,
    private val remoteMediator: NewsRemoteMediator,
) :
    NewsRepository {

    override suspend fun getNews(isRefresh: Boolean): List<TopNewsEntity> =
        withContext(Dispatchers.IO) {
            if (!newsDao.getAllNews().isNullOrEmpty() && !isRefresh) {
                return@withContext newsDao.getAllNews()!!
            }

            val response = api.getTopHeadlines()
            val deferreds = mutableListOf<Deferred<TopNewsEntity>>()
            if (response.isSuccessful && response.body() != null) {
                if (isRefresh) {
                    newsDao.deleteAllNews()
                }
                for (news in response.body()!!.articles) {
                    deferreds.add(
                        async {
                            with(news) {
                                return@async TopNewsEntity(
                                    url = url,
                                    urlToImage = urlToImage,
                                    title = title,
                                    description = description ?: ""
                                )
                            }

                        }
                    )
                }
            }

            for (deferred in deferreds) {
                newsDao.insertNews(deferred.await())
            }

            return@withContext newsDao.getAllNews() ?: emptyList()
        }

    override suspend fun getSources(isRefresh: Boolean): List<SourceItem> =
        withContext(Dispatchers.IO) {
            if (!sourceDao.getAllSource().isNullOrEmpty() && !isRefresh) {
                return@withContext sourceDao.getAllSource()!!
            }

            val response = api.getAllSource()
            val deferreds = mutableListOf<Deferred<SourceItem>>()
            if (response.isSuccessful && response.body() != null) {
                if (isRefresh) {
                    sourceDao.deleteAllSource()
                }
                for (source in response.body()!!.sources) {
                    deferreds.add(
                        async {
                            with(source) {
                                return@async SourceItem(
                                    category = category,
                                    country = country,
                                    description = description,
                                    id = id,
                                    language = language,
                                    name = name,
                                    url = url
                                )
                            }

                        }
                    )
                }
            }

            for (deferred in deferreds) {
                sourceDao.insertSource(deferred.await())
            }

            return@withContext sourceDao.getAllSource() ?: emptyList()
        }

    @ExperimentalPagingApi
    override fun getEverything(query: String): Flow<PagingData<TopNewsEntity>> {
        val pagingSourceFactory = { pagingNewsDao.getAllNews() }
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
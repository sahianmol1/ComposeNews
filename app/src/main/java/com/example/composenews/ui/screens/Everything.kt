package com.example.composenews.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.composenews.ui.models.NewsUIModel
import com.example.composenews.utils.ListType
import com.example.composenews.utils.SortBy
import com.example.composenews.viewmodels.TopNewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalFoundationApi
@Composable
fun Everything(viewModel: TopNewsViewModel, listType: ListType, sortBy: SortBy, toolbarHeight: Dp) {

    val searchQuery by viewModel.searchQuery
    val isLoading = viewModel.isLoading.observeAsState().value

    viewModel.searchNews(searchQuery, sortBy)

    val articles = viewModel.searchedNews.collectAsLazyPagingItems()

    val swipeRefreshState = rememberSwipeRefreshState(isLoading ?: false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.searchNews(searchQuery, sortBy) }) {
        when (listType) {
            ListType.LIST -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = articles,
                        key = { article ->
                            article.id!!
                        }
                    ) { article ->
                        article?.let {
                            if (article == articles[0]) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(toolbarHeight)
                                ) {
                                }
                            }
                            NewsCard(
                                item = NewsUIModel(
                                    url = it.url,
                                    urlToImage = it.urlToImage,
                                    title = it.title,
                                    description = it.description,
                                ), listType = listType
                            )
                        }
                    }
                }
            }
            ListType.GRID -> {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(articles.itemCount) { index ->
                        val article = articles[index]
                        article?.let {
                            NewsCard(
                                item = NewsUIModel(
                                    url = it.url,
                                    urlToImage = it.urlToImage,
                                    title = it.title,
                                    description = it.description,
                                ), listType = listType
                            )
                        }
                    }
                }
            }
            ListType.STAGGERED -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {

                    StaggeredVerticalGrid(maxColumnWidth = 220.dp) {
                        articles.itemSnapshotList.forEach { article ->
                            article?.let {
                                if (article == articles[0]) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .requiredHeight(toolbarHeight)
                                    ) {
                                    }
                                }
                                with(it) {
                                    NewsCard(
                                        NewsUIModel(
                                            url = url,
                                            urlToImage = urlToImage,
                                            title = title,
                                            description = description,
                                        ), listType
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

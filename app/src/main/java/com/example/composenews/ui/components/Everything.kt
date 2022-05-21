package com.example.composenews.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.composenews.data.local.remotemediator.PagingNewsEntity
import com.example.composenews.ui.models.NewsUIModel
import com.example.composenews.utils.ListType
import com.example.composenews.viewmodels.TopNewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Everything(viewModel: TopNewsViewModel, listType: ListType) {
    val articles: LazyPagingItems<PagingNewsEntity> =
        viewModel.getEverything("bitcoin").collectAsLazyPagingItems()

    when (listType) {
        ListType.LIST, ListType.STAGGERED -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = articles,
                    key = { article ->
                        article.url
                    }
                ) { article ->
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
    }
}

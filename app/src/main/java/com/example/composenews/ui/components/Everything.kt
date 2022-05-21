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
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.utils.ListType
import com.example.composenews.viewmodels.TopNewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Everything(viewModel: TopNewsViewModel, listType: ListType) {
    val articles : LazyPagingItems<TopNewsEntity> = viewModel.getEverything("bitcoin").collectAsLazyPagingItems()

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
                    key = {  article ->
                        article.url
                    }
                ) { article ->
                    NewsCard(item = article!!, listType = listType)
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
                    NewsCard(articles[index]!!, listType = listType)
                }
            }
        }
    }
}

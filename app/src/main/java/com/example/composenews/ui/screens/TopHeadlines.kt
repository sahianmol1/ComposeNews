package com.example.composenews.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.ui.models.NewsUIModel
import com.example.composenews.utils.ListType
import com.example.composenews.viewmodels.TopNewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopHeadlines(
    navController: NavController? = null,
    viewModel: TopNewsViewModel? = null,
    listType: ListType,
    toolbarHeight: Dp,
) {
    val articles: List<TopNewsEntity> = viewModel!!.news.observeAsState(listOf()).value
    val isLoading = viewModel.isLoading.observeAsState().value

    val swipeRefreshState = rememberSwipeRefreshState(isLoading ?: false)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getNews(true) }) {

        when (listType) {
            ListType.LIST -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(articles) { article ->
                        with(article) {
                            if (article == articles[0]) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(toolbarHeight)
                                ) {
                                }
                            }
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
            ListType.GRID -> {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(articles) { article ->
                        with(article) {
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
            ListType.STAGGERED -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    StaggeredVerticalGrid(maxColumnWidth = 220.dp) {
                        articles.forEach { article ->
                            with(article) {
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

@Preview(showBackground = true)
@Composable
fun PreviewHeadlines() {
    TopHeadlines(listType = ListType.LIST, toolbarHeight = 60.dp)
}
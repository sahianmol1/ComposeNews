package com.example.composenews.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composenews.R
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.viewmodels.TopNewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopHeadlines(
    navController: NavController? = null,
    viewModel: TopNewsViewModel? = null,
    isGridView: Boolean
) {
    val articles: List<TopNewsEntity> = viewModel!!.news.observeAsState(listOf()).value
    val isLoading = viewModel.isLoading.observeAsState().value

    when (viewModel.eventFlow.collectAsState(0).value) {
        is TopNewsViewModel.CustomEvent.ErrorEvent -> {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = { viewModel.getNews(true) })
                    { Text(text = "Retry") }
                },
                dismissButton = {
                    TextButton(onClick = {})
                    { Text(text = "Cancel") }
                },
                title = { Text(text = "Error Occurred") },
                text = { Text(text = stringResource(R.string.error_content)) },
            )
        }
    }


    val swipeRefreshState = rememberSwipeRefreshState(isLoading ?: false)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getNews(true) }) {
        if (isGridView) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(articles) { article ->
                    NewsCard(article)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(articles) { article ->
                    NewsCard(article)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHeadlines() {
    TopHeadlines(isGridView = false)
}
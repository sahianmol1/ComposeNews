package com.example.composenews.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.viewmodels.TopNewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun Sources(viewModel: TopNewsViewModel?) {
    viewModel!!.getAllSource(false)
    val sources: List<SourceItem> = viewModel.source.observeAsState(listOf()).value
    val isLoading = viewModel.isLoading.observeAsState().value

    val swipeRefreshState = SwipeRefreshState(isLoading ?: false)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getAllSource(true) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            items(sources) { source ->
                SourceCard(source)
            }
        }
    }
}
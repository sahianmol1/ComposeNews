package com.example.composenews.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.utils.ListType
import com.example.composenews.viewmodels.TopNewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Sources(viewModel: TopNewsViewModel?, listType: ListType) {
    viewModel!!.getAllSource(false)
    val sources: List<SourceItem> = viewModel.source.observeAsState(listOf()).value
    val isLoading = viewModel.isLoading.observeAsState().value

    val swipeRefreshState = SwipeRefreshState(isLoading ?: false)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getAllSource(true) }) {

        when (listType) {
            ListType.LIST -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(sources) { source ->
                        SourceCard(source, listType)
                    }
                }
            }
            ListType.GRID -> {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2), modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(sources) { source ->
                        SourceCard(source, listType)
                    }
                }
            }
            ListType.STAGGERED -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    StaggeredVerticalGrid(maxColumnWidth = 220.dp) {
                        sources.forEach{ source ->
                            SourceCard(source, listType)
                        }
                    }
                }
            }
        }

    }
}
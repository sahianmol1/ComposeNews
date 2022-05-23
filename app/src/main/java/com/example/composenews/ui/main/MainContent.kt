package com.example.composenews.ui.main

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenews.ui.components.AppBar
import com.example.composenews.ui.components.Everything
import com.example.composenews.ui.components.Sources
import com.example.composenews.ui.components.TopHeadlines
import com.example.composenews.ui.components.bottomappbar.AppBottomNavigation
import com.example.composenews.ui.components.bottomappbar.NavRoutes.EVERYTHING
import com.example.composenews.ui.components.bottomappbar.NavRoutes.SOURCES
import com.example.composenews.ui.components.bottomappbar.NavRoutes.TOP_HEADLINES
import com.example.composenews.utils.Constants.DefaultToolbarHeight
import com.example.composenews.utils.ListType
import com.example.composenews.utils.SortBy
import com.example.composenews.viewmodels.TopNewsViewModel
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun MainContent(context: Context, viewModel: TopNewsViewModel, modifier: Modifier) {
    val navController = rememberNavController()

    var listType by rememberSaveable { mutableStateOf(ListType.LIST) }
    var sortBy by rememberSaveable { mutableStateOf(SortBy.popularity) }

    val toolbarHeight = DefaultToolbarHeight
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
        ) {

            NavHost(navController = navController, startDestination = TOP_HEADLINES) {
                composable(TOP_HEADLINES) {
                    TopHeadlines(navController, viewModel, listType, toolbarHeight)
                }

                composable(EVERYTHING) {
                    Everything(viewModel, listType, sortBy, toolbarHeight)
                }

                composable(SOURCES) {
                    Sources(viewModel, listType, toolbarHeight)
                }
            }

            AppBar(
                modifier = modifier
                    .height(toolbarHeight)
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
                navController = navController,
                context = context,
                viewModel = viewModel,
                {
                    listType = it
                }) {
                sortBy = it
            }
        }
    }
}
package com.example.composenews.ui.main

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.composenews.utils.ListType
import com.example.composenews.utils.SortBy
import com.example.composenews.viewmodels.TopNewsViewModel

@Composable
fun MainContent(context: Context, viewModel: TopNewsViewModel) {
    val navController = rememberNavController()

    var listType by rememberSaveable { mutableStateOf(ListType.LIST) }
    var sortBy by rememberSaveable { mutableStateOf(SortBy.popularity) }

    Scaffold(
        topBar = {
            AppBar(navController = navController, context = context, {
                listType = it
            }) {
                sortBy = it
            }
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) {
        Surface() {
            NavHost(navController = navController, startDestination = TOP_HEADLINES) {
                composable(TOP_HEADLINES) {
                    TopHeadlines(navController, viewModel, listType)
                }

                composable(EVERYTHING) {
                    Everything(viewModel, listType, sortBy)
                }

                composable(SOURCES) {
                    Sources(viewModel)
                }
            }
        }
    }
}
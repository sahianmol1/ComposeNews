package com.example.composenews.ui.main

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenews.ui.components.AppBar
import com.example.composenews.ui.components.bottomappbar.NavRoutes.EVERYTHING
import com.example.composenews.ui.components.bottomappbar.NavRoutes.SOURCES
import com.example.composenews.ui.components.bottomappbar.NavRoutes.TOP_HEADLINES
import com.example.composenews.ui.components.Everything
import com.example.composenews.ui.components.Sources
import com.example.composenews.ui.components.TopHeadlines
import com.example.composenews.ui.components.bottomappbar.AppBottomNavigation
import com.example.composenews.viewmodels.TopNewsViewModel

@Composable
fun MainContent(context: Context, viewModel: TopNewsViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            AppBar(navController = navController, context = context)
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) {
        Surface() {
            NavHost(navController = navController, startDestination = TOP_HEADLINES) {
                composable(TOP_HEADLINES) {
                    TopHeadlines(navController, viewModel, true)
                }

                composable(EVERYTHING) {
                    Everything()
                }

                composable(SOURCES) {
                    Sources(viewModel)
                }
            }
        }
    }
}
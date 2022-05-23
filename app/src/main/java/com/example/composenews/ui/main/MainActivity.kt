package com.example.composenews.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Modifier
import com.example.composenews.ui.theme.ComposeNewsTheme
import com.example.composenews.viewmodels.TopNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val viewModel: TopNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNews(false)

        setContent {
            ComposeNewsTheme() {
                MainContent(this, viewModel, Modifier)
            }
        }

    }
}


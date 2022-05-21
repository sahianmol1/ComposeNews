package com.example.composenews.ui.components

import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composenews.R
import com.example.composenews.ui.components.bottomappbar.NavRoutes
import com.example.composenews.utils.ListType
import com.example.composenews.utils.SortBy

@Composable
fun AppBar(navController: NavController, context: Context, onMenuItemClick: (ListType) -> Unit, onSortItemClick: (SortBy) -> Unit) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    var showSortMenu by rememberSaveable {
        mutableStateOf(false)
    }
    var showSearch by rememberSaveable {
        mutableStateOf(false)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val name: String = when (navBackStackEntry?.destination?.route) {
        NavRoutes.TOP_HEADLINES -> {
            showSearch = false
            context.getString(R.string.top_headlines)
        }
        NavRoutes.EVERYTHING -> {
            showSearch = true
            context.getString(R.string.everything)
        }
        NavRoutes.SOURCES -> {
            showSearch = false
            context.getString(R.string.sources)
        }
        else -> {
            showSearch = false
            context.getString(R.string.top_headlines)
        }
    }

    TopAppBar(
        title = { Text(text = name) },
        actions = {
            if (showSearch) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, "")
                }
                IconButton(onClick = { showSortMenu = !showSortMenu }) {
                    Icon(Icons.Default.List, "")
                }

                DropdownMenu(expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {

                    DropdownMenuItem(onClick = {
                        showSortMenu = false
                        onSortItemClick(SortBy.popularity)
                    }) {
                        Text(text = "Popularity")
                    }

                    DropdownMenuItem(onClick = {
                        showSortMenu = false
                        onSortItemClick(SortBy.publishedAt)
                    }) {
                        Text(text = "Published At")
                    }

                }
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = {
                    onMenuItemClick(ListType.LIST)
                    showMenu = false
                }) {
                    Text(text = "List")
                }

                DropdownMenuItem(onClick = {
                    onMenuItemClick(ListType.GRID)
                    showMenu = false
                }) {
                    Text(text = "Grid")
                }

                DropdownMenuItem(onClick = {
                    onMenuItemClick(ListType.STAGGERED)
                    showMenu = false
                }) {
                    Text(text = "Staggered")
                }
            }
        }
    )
}
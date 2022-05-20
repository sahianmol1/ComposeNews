package com.example.composenews.ui.components

import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composenews.R
import com.example.composenews.ui.components.bottomappbar.NavRoutes
import com.example.composenews.utils.ListType

@Composable
fun AppBar(navController: NavController, context: Context, onMenuItemClick: (ListType) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val name: String = when(navBackStackEntry?.destination?.route) {
        NavRoutes.TOP_HEADLINES -> context.getString(R.string.top_headlines)
        NavRoutes.EVERYTHING -> context.getString(R.string.everything)
        NavRoutes.SOURCES -> context.getString(R.string.sources)
        else -> context.getString(R.string.top_headlines)
    }

    TopAppBar(
        title = { Text(text = name)},
        actions = {
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
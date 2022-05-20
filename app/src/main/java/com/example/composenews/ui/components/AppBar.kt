package com.example.composenews.ui.components

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composenews.R
import com.example.composenews.ui.components.bottomappbar.NavRoutes
import com.example.composenews.ui.theme.white

@Composable
fun AppBar(navController: NavController, context: Context) {
    TopAppBar(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val name: String = when(navBackStackEntry?.destination?.route) {
            NavRoutes.TOP_HEADLINES -> context.getString(R.string.top_headlines)
            NavRoutes.EVERYTHING -> context.getString(R.string.everything)
            NavRoutes.SOURCES -> context.getString(R.string.sources)
            else -> context.getString(R.string.top_headlines)
        }
        Text(text = name, fontSize = 20.sp, color = white, fontWeight = FontWeight.Bold)
    }
}
package com.example.composenews.ui.components.bottomappbar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navItems = listOf(NavItem.TopHeadlines, NavItem.Everything, NavItem.Sources)

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route) },
                icon = {
                    Icon(painter = painterResource(id = it.icon), contentDescription = "")
                },
                label = {Text(text = stringResource(id = it.name))}
            )

        }

    }
}
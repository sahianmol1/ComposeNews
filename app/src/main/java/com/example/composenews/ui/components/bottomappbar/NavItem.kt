package com.example.composenews.ui.components.bottomappbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.composenews.R
import com.example.composenews.ui.components.bottomappbar.NavRoutes.EVERYTHING
import com.example.composenews.ui.components.bottomappbar.NavRoutes.SOURCES
import com.example.composenews.ui.components.bottomappbar.NavRoutes.TOP_HEADLINES

sealed class NavItem(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val route: String
){
    object TopHeadlines: NavItem(R.string.top_headlines, R.drawable.ic_baseline_article_24, TOP_HEADLINES)
    object Everything: NavItem(R.string.everything, R.drawable.ic_everything, EVERYTHING)
    object Sources: NavItem(R.string.sources, R.drawable.ic_source, SOURCES)
}

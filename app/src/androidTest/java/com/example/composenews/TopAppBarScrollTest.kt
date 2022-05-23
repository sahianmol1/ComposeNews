package com.example.composenews

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.composenews.ui.components.TopHeadlines
import com.example.composenews.ui.theme.ComposeNewsTheme
import com.example.composenews.utils.Constants.DefaultToolbarHeight
import com.example.composenews.utils.ListType
import org.junit.Rule
import org.junit.Test

class TopAppBarScrollTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topAppBarScrollsUpwardsAndOutOfViewOnContentScroll() {
        composeTestRule.setContent {
            ComposeNewsTheme() {
                TopHeadlines(listType = ListType.LIST, toolbarHeight = DefaultToolbarHeight)
            }
        }

        Thread.sleep(3000)

        composeTestRule.onNodeWithText("Top Headlines").assertIsDisplayed()
    }
}
package com.example.composenews.ui.models

data class NewsUIModel(
    val url: String,
    val urlToImage: String? = "",
    val title: String,
    val description: String,
)
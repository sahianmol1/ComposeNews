package com.example.composenews.data.remote.models

data class SourceResponse(
    val sources: List<SourceItem>,
    val status: String
)
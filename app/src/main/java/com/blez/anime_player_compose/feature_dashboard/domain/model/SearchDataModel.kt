package com.blez.anime_player_compose.feature_dashboard.domain.model

data class SearchDataModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<SearchResult>
)
data class SearchResult(
    val id: String,
    val image: String,
    val releaseDate: String,
    val subOrDub: String,
    val title: String,
    val url: String
)
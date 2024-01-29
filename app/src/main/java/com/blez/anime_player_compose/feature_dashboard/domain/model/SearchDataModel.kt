package com.blez.anime_player_compose.feature_dashboard.domain.model

data class SearchDataModel(
    val currentPage: Any,
    val hasNextPage: Boolean,
    val results: List<Search_Result>,
    val totalPages: Any
)

data class Search_Result(
    val dub: Int,
    val duration: String,
    val episodes: Int,
    val id: String,
    val image: String,
    val japaneseTitle: String,
    val nsfw: Boolean,
    val sub: Int,
    val title: String,
    val type: String,
    val url: String
)
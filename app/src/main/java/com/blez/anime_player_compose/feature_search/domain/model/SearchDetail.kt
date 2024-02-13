package com.blez.anime_player_compose.feature_search.domain.model

data class SearchDetail(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Search_Result>
)


data class Search_Result(
    val id: String,
    val image: String,
    val releaseDate: String,
    val subOrDub: String,
    val title: String,
    val url: String
)
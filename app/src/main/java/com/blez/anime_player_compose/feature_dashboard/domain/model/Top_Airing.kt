package com.blez.anime_player_compose.feature_dashboard.domain.model

data class Top_Airing(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Top_Airing_Result>
)

data class Top_Airing_Result(
    val genres: List<String>,
    val id: String,
    val image: String,
    val title: String,
    val url: String
)
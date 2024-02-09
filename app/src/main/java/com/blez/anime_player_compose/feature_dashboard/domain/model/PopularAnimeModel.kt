package com.blez.anime_player_compose.feature_dashboard.domain.model

data class PopularAnimeModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<ResultX>
)
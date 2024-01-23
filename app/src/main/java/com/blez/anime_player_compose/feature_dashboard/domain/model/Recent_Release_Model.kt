package com.blez.anime_player_compose.feature_dashboard.domain.model

data class Recent_Release_Model(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Result>
)
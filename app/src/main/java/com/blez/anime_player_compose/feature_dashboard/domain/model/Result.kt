package com.blez.anime_player_compose.feature_dashboard.domain.model

data class Result(
    val episodeId: String,
    val episodeNumber: Int,
    val id: String,
    val image: String,
    val title: String,
    val url: String
)
package com.blez.anime_player_compose.feature_detail_info.domain.model

data class Episode(
    val id: String,
    val isFiller: Boolean,
    val number: Int,
    val title: String,
    val url: String
)
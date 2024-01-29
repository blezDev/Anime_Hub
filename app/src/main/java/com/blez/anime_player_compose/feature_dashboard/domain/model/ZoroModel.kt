package com.blez.anime_player_compose.feature_dashboard.domain.model

data class ZoroModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Zoro_Result>,
    val totalPages: Int
)

data class Zoro_Result(
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
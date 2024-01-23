package com.blez.anime_player_compose.feature_dashboard.domain.model

data class AnimeDetails(
    val description: String,
    val episodes: List<Episode>,
    val genres: List<String>,
    val id: String,
    val image: String,
    val otherName: String,
    val releaseDate: String,
    val status: String,
    val subOrDub: String,
    val title: String,
    val totalEpisodes: Int,
    val type: String,
    val url: String
)
data class Episode(
    val id: String,
    val number: Int,
    val url: String
)
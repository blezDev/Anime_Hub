package com.blez.anime_player_compose.feature_dashboard.domain.model

data class AnimeDetails(
    val alID: Int,
    val description: String,
    val episodes: List<Episode>,
    val hasDub: Boolean,
    val hasSub: Boolean,
    val id: String,
    val image: String,
    val malID: Int,
    val subOrDub: String,
    val title: String,
    val totalEpisodes: Int,
    val type: String,
    val url: String
)

data class Episode(
    val id: String,
    val isFiller: Boolean,
    val number: Int,
    val title: String,
    val url: String
)







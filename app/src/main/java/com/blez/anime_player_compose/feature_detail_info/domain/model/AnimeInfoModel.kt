package com.blez.anime_player_compose.feature_detail_info.domain.model



data class AnimeInfoModel(
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


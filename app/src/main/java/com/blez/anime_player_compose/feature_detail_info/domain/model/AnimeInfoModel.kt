package com.blez.anime_player_compose.feature_detail_info.domain.model



/*data class AnimeInfoModel(
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
)*/


data class AnimeInfoModel(
    val description: String,
    val episodes: List<Gogoanime_Episode>,
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

data class Gogoanime_Episode(
    val id: String,
    val number: Int,
    val url: String
)
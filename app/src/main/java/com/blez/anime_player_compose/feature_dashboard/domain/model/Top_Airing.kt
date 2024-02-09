package com.blez.anime_player_compose.feature_dashboard.domain.model

/*data class Top_Airing(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Top_Airing_Result>,
    val totalPages: Int
)*/


data class Top_Airing(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Result>
)

/*data class Top_Airing_Result(
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
)*/

data class Top_Model(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Result>
)

data class Top_Airing_Result(
    val genres: List<String>,
    val id: String,
    val image: String,
    val title: String,
    val url: String
)
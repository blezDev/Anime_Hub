package com.blez.anime_player_compose.feature_video.domain.model

/*
data class VideoModel(
    val intro: Intro,
    val sources: List<Source>,
    val subtitles: List<Subtitle>
)
*/

data class VideoModel(
    val download: String,
    val headers: Headers,
    val sources: List<Source>
)

data class Headers(
    val Referer: String
)

data class Source(
    val isM3U8: Boolean,
    val quality: String,
    val url: String
)
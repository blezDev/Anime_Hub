package com.blez.anime_player_compose.feature_video.domain.model

data class VideoModel(
    val intro: Intro,
    val sources: List<Source>,
    val subtitles: List<Subtitle>
)
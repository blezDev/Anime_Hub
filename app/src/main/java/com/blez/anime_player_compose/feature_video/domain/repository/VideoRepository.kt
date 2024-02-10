package com.blez.anime_player_compose.feature_video.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel

interface VideoRepository {

    suspend fun getVideoLinks(episodeId : String) : ResultState<VideoModel>
}
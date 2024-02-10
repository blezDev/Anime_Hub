package com.blez.anime_player_compose.feature_video.data.repository

import android.content.Context
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.common.util.RunningCache
import com.blez.anime_player_compose.common.util.checkForInternetConnection
import com.blez.anime_player_compose.feature_video.data.remote.WatchAPI
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import com.blez.anime_player_compose.feature_video.domain.repository.VideoRepository

class VideoRepositoryImpl(val api: WatchAPI, val context: Context) : VideoRepository {
    override suspend fun getVideoLinks(episodeId: String): ResultState<VideoModel> {
        try {
            val cache = RunningCache.videoData[episodeId]
            if (!context.checkForInternetConnection()) {
                return ResultState.Error("Couldn\'t reach server. Check your internet connection.")
            }
            if (cache != null) {
                return ResultState.Success(cache)
            }

            val result = api.getVideoLink(episodeId)
            if (result.code() == 200 && result.body() != null) {
                return ResultState.Success(data = result.body())
            }
            return ResultState.Error("Something have occurred.")

        } catch (e: Exception) {
            return ResultState.Error(e.message.toString())
        }
    }
}
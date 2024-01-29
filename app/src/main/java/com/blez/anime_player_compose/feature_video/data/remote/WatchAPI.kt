package com.blez.anime_player_compose.feature_video.data.remote

import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchAPI {

    @GET("/anime/zoro/watch")
    suspend fun getTopAiring(@Query("episodeId") episodeId: String): Response<VideoModel>
}
package com.blez.anime_player_compose.feature_video.data.remote

import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchAPI {

    @GET("/anime/gogoanime/watch/{episodeId}")
    suspend fun getVideoLink(@Path("episodeId") episodeId: String): Response<VideoModel>
}
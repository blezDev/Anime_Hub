package com.blez.anime_player_compose.feature_detail_info.data.remote

import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoAPI {

    @GET("/anime/zoro/info")
    suspend fun getAnimeInfo(@Query("id") animeId : String) : Response<AnimeInfoModel>
}
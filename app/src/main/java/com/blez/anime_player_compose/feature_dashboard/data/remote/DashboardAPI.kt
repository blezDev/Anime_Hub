package com.blez.anime_player_compose.feature_dashboard.data.remote

import com.blez.anime_player_compose.feature_dashboard.domain.model.AnimeDetails
import com.blez.anime_player_compose.feature_dashboard.domain.model.GoogleProfileModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.SearchDataModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardAPI {
    @GET("/anime/zoro/recent-episodes")
    suspend fun getRecentRelease(@Query("page") page: Int): Response<ZoroModel>

    @GET("/anime/zoro/recent-added")
    suspend fun getRecentAdded(@Query("page") page: Int): Response<ZoroModel>

    @GET("/anime/zoro/latest-completed")
    suspend fun getCompletedAnime(@Query("page") page: Int): Response<ZoroModel>


    @GET("/anime/zoro/top-airing")
    suspend fun getTopAiring(@Query("page") page: Int) : Response<Top_Airing>





}
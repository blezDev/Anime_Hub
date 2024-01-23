package com.blez.anime_player_compose.feature_dashboard.data.remote

import com.blez.anime_player_compose.feature_dashboard.domain.model.AnimeDetails
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.SearchDataModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardAPI {
    @GET("/anime/gogoanime/recent-episodes")
    suspend fun getRecentRelease(@Query("page") page: Int): Response<Recent_Release_Model>


    @GET("/anime/gogoanime/info/{animeId}")
    suspend fun getAnimeDetails(@Path(value = "animeId") animeId: String): Response<AnimeDetails>

    @GET("/anime/gogoanime/top-airing")
    suspend fun getTopAiring(@Query("page") page: Int) : Response<Top_Airing>

    @GET("/anime/gogoanime/{animeSearch}")
    suspend fun getAnimeSearch(@Path("animeSearch") animeSearch: String): Response<SearchDataModel>

}
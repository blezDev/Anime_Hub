package com.blez.anime_player_compose.feature_dashboard.data.remote

import com.blez.anime_player_compose.feature_dashboard.domain.model.GoogleProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleOuthAPI {
    @GET("/tokeninfo")
    suspend fun getGoogleProfile(@Query("id_token") tokenId : String) : Response<GoogleProfileModel>
}
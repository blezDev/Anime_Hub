package com.blez.anime_player_compose.feature_search.data.remote

import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchAPI {

    @GET("/anime/gogoanime/{query}")
    suspend fun getSearchAnime(@Path("query") query: String) : Response<SearchDetail>
}
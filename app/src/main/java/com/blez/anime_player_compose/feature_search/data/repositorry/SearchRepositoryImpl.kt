package com.blez.anime_player_compose.feature_search.data.repositorry

import android.content.Context
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.common.util.RunningCache
import com.blez.anime_player_compose.common.util.checkForInternetConnection
import com.blez.anime_player_compose.feature_search.data.remote.SearchAPI
import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail
import com.blez.anime_player_compose.feature_search.domain.repository.SearchRepository

class SearchRepositoryImpl(val api: SearchAPI, val context: Context) : SearchRepository {
    override suspend fun getSearchAnime(query: String): ResultState<SearchDetail> {
        try {
            val cache = RunningCache.searchData[query]
            if (!context.checkForInternetConnection()) {
                return ResultState.Error("Couldn\'t reach server. Check your internet connection.")
            }
            if (cache != null) {
                return ResultState.Success(data = cache)
            }
            val result = api.getSearchAnime(query)
            if (result.code() == 200 && result.body() != null) {
                RunningCache.searchData[query] = result.body()!!
                return ResultState.Success(data = result.body())
            }
            return ResultState.Error("Something went wrong")

        } catch (e: Exception) {
            return ResultState.Error(e.message)
        }
    }

}
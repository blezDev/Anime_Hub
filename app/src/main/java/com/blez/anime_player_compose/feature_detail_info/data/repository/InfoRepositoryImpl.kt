package com.blez.anime_player_compose.feature_detail_info.data.repository

import android.content.Context
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.common.util.RunningCache
import com.blez.anime_player_compose.common.util.checkForInternetConnection
import com.blez.anime_player_compose.feature_detail_info.data.remote.InfoAPI
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel
import com.blez.anime_player_compose.feature_detail_info.domain.repository.InfoRepository

class InfoRepositoryImpl(val context: Context, val infoAPI: InfoAPI) : InfoRepository {

    override suspend fun getAnimeInfo(animeId: String, page: Int): ResultState<AnimeInfoModel> {
        val cache = RunningCache.animeDetails
        if (!context.checkForInternetConnection()) {
            return ResultState.Error("Couldn\'t reach server. Check your internet connection.")
        }
        if (cache[animeId] != null) {
            return ResultState.Success(data = cache[animeId])
        } else {
            val result = infoAPI.getAnimeInfo(animeId, page)
            if (result.code() == 200 && result.body() != null) {
                return ResultState.Success(data = result.body())
            }

        }
        return ResultState.Error("Something went wrong")

    }
}
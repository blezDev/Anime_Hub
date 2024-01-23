package com.blez.anime_player_compose.feature_dashboard.data.repository

import android.content.Context
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.common.util.RunningCache
import com.blez.anime_player_compose.common.util.checkForInternetConnection
import com.blez.anime_player_compose.feature_dashboard.data.remote.DashboardAPI
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository

class DashboardRepositoryImpl(
    private val context: Context,
    private val dashboardAPI: DashboardAPI
) : DashboardRepository {
    override suspend fun getRecentRelease(page: Int): ResultState<Recent_Release_Model> {
        if (!context.checkForInternetConnection()) {
            return ResultState.Error("Couldn\'t reach server. Check your internet connection.")
        }
        if (RunningCache.recentReleaseCache[page] != null) {
            return ResultState.Success(data = RunningCache.recentReleaseCache[page])
        }
        val result = dashboardAPI.getRecentRelease(page)
        if (result.code() == 200) {
            return ResultState.Success(data = result.body())
        }
        return ResultState.Error("Something went wrong")
    }

    override suspend fun getTopAiring(page: Int): ResultState<Top_Airing> {
        val cache = RunningCache.topAiringCache
        if (!context.checkForInternetConnection()) {
            return ResultState.Error("Couldn\'t reach server. Check your internet connection.")
        }
        if (cache[page] != null){
            return ResultState.Success(data = cache[page])
        }
        val result = dashboardAPI.getTopAiring(page)
        if (result.code()==200 && result.body() != null){
            return ResultState.Success(data = result.body())
        }
        return ResultState.Error("Something went wrong")
    }
}
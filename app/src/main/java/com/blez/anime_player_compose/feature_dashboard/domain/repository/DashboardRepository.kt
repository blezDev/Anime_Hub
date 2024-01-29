package com.blez.anime_player_compose.feature_dashboard.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel

interface DashboardRepository {

    suspend fun getRecentRelease(page : Int = 1) : ResultState<ZoroModel>

    suspend fun getTopAiring(page: Int = 1) : ResultState<Top_Airing>

    suspend fun getRecentAdded(page: Int = 1) : ResultState<ZoroModel>

    suspend fun getCompletedAnime(page: Int = 1) : ResultState<ZoroModel>

}
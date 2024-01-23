package com.blez.anime_player_compose.feature_dashboard.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing

interface DashboardRepository {

    suspend fun getRecentRelease(page : Int = 1) : ResultState<Recent_Release_Model>

    suspend fun getTopAiring(page: Int = 1) : ResultState<Top_Airing>

}
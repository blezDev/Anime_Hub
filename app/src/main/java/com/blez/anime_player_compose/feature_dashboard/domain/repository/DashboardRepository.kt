package com.blez.anime_player_compose.feature_dashboard.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.core.entries.ListEntity
import com.blez.anime_player_compose.feature_dashboard.domain.model.MovieModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.PopularAnimeModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {

    suspend fun getRecentRelease(page: Int = 1): ResultState<ZoroModel>

    suspend fun getTopAiring(page: Int = 1): ResultState<Top_Airing>

    suspend fun getPopularAnime(page: Int = 1): ResultState<PopularAnimeModel>

    suspend fun getMoviesAnime(page: Int = 1): ResultState<MovieModel>

    fun getAnimeList(): Flow<List<ListEntity>>

    suspend fun insertAnimeList(data : ListEntity)


}
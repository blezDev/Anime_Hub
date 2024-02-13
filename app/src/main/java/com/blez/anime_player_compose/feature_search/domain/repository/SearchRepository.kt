package com.blez.anime_player_compose.feature_search.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail


interface SearchRepository {

    suspend fun getSearchAnime(query : String) : ResultState<SearchDetail>
}
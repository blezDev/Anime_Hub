package com.blez.anime_player_compose.feature_search.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail
import com.blez.anime_player_compose.feature_search.domain.repository.SearchRepository
import kotlin.jvm.Throws

class SearchUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(query : String): ResultState<SearchDetail> {
        @Throws(InvalidAPIException::class)
        if (query.isNotEmpty()){
            return searchRepository.getSearchAnime(query)
        } else {
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}
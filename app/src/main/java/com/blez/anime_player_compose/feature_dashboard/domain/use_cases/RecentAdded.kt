package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.MovieModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import kotlin.jvm.Throws

class MoviesAddedUseCase(private val repository: DashboardRepository){
    suspend operator fun invoke(page : Int): ResultState<MovieModel> {
        @Throws(InvalidAPIException::class)
        if (page > 0){
            return repository.getMoviesAnime(page)
        }else{
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}
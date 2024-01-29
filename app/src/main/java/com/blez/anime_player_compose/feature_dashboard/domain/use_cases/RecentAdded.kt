package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import kotlin.jvm.Throws

class RecentAddedUseCase(private val repository: DashboardRepository){
    suspend operator fun invoke(page : Int): ResultState<ZoroModel> {
        @Throws(InvalidAPIException::class)
        if (page > 0){
            return repository.getRecentAdded(page)
        }else{
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}
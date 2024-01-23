package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import kotlin.jvm.Throws

class RecentReleaseUseCase(private val repository: DashboardRepository) {

    suspend operator fun invoke(page : Int): ResultState<Recent_Release_Model> {
        @Throws(InvalidAPIException::class)
        if (page > 0){
           return repository.getRecentRelease(page)
        }else{
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}
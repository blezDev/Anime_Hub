package com.blez.anime_player_compose.feature_video.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import com.blez.anime_player_compose.feature_video.domain.repository.VideoRepository
import kotlin.jvm.Throws

class VideoLinksUseCases(val repository: VideoRepository) {
    suspend operator fun invoke(episodeId : String): ResultState<VideoModel> {
        @Throws(InvalidAPIException::class)
           if (episodeId.isNotEmpty()) {
            return repository.getVideoLinks(episodeId)
        } else {
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}
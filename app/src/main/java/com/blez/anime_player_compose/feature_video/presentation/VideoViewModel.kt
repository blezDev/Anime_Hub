package com.blez.anime_player_compose.feature_video.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.core.entries.ListEntity
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.DashboardUseCases
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(val dashboardUseCases: DashboardUseCases) : ViewModel() {
    sealed class VideoUIEvent {
        data object Loading : VideoUIEvent()

        data class Success(val data: VideoModel) : VideoUIEvent()

        data class Failure(val message: String) : VideoUIEvent()
    }

    private val _fetchVideoState = MutableStateFlow<VideoUIEvent>(VideoUIEvent.Loading)
    val fetchVideoState: StateFlow<VideoUIEvent>
        get() = _fetchVideoState

    fun fetchVideo(episodeId: String) {
        viewModelScope.launch {
            _fetchVideoState.emit(VideoUIEvent.Loading)
            when (val result = dashboardUseCases.videoLinksUseCases(episodeId)) {
                is ResultState.Error -> {
                    _fetchVideoState.emit(VideoUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchVideoState.emit(VideoUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _fetchVideoState.emit(VideoUIEvent.Success(data = result.data))
                    else
                        _fetchVideoState.emit(VideoUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun saveHistory(
        animeId: String,
        image: String,
        title: String,
        episodeId: String,
        episodeNumber: String
    ) {
        viewModelScope.launch {
            dashboardUseCases.insertAnimeUseCase(
                data = ListEntity(
                    animeId = animeId,
                    image = image,
                    title = title,
                    episodeId = episodeId,
                    episodeNo = episodeNumber
                )
            )
        }
    }


}
package com.blez.anime_player_compose.feature_dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.MovieModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.PopularAnimeModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.DashboardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(val dashboardUseCases: DashboardUseCases) :
    ViewModel() {

    sealed class UIEvent {
        data object Loading : UIEvent()
        data class Success(val data: ZoroModel) : UIEvent()
        data class Failure(val message: String) : UIEvent()

    }

    sealed class AiringUIEvent {
        data object Loading : AiringUIEvent()
        data class Success(val data: Top_Airing) : AiringUIEvent()
        data class Failure(val message: String) : AiringUIEvent()
    }

    sealed class PopularUIEvent {
        data object Loading : PopularUIEvent()
        data class Success(val data: PopularAnimeModel?) : PopularUIEvent()
        data class Failure(val message: String) : PopularUIEvent()
    }

    sealed class MovieAnimeUIEvent {
        data object Loading : MovieAnimeUIEvent()
        data class Success(val data: MovieModel?) : MovieAnimeUIEvent()
        data class Failure(val message: String) : MovieAnimeUIEvent()

    }

    private val _fetchState = MutableStateFlow<UIEvent>(UIEvent.Loading)
    val fetchState: StateFlow<UIEvent>
        get() = _fetchState

    private val _topAiringState = MutableStateFlow<AiringUIEvent>(AiringUIEvent.Loading)
    val topAiringState: StateFlow<AiringUIEvent>
        get() = _topAiringState


    private val _fetchRecentAdded = MutableStateFlow<PopularUIEvent>(PopularUIEvent.Loading)
    val fetchRecentAdded: StateFlow<PopularUIEvent>
        get() = _fetchRecentAdded

    private val _fetchCompletedAnime =
        MutableStateFlow<MovieAnimeUIEvent>(MovieAnimeUIEvent.Loading)
    val fetchCompletedAnime: StateFlow<MovieAnimeUIEvent>
        get() = _fetchCompletedAnime

    fun fetchTopAiring(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.topAiringUseCase(page)) {
                is ResultState.Error -> {
                    _topAiringState.emit(AiringUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _topAiringState.emit(AiringUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _topAiringState.emit(AiringUIEvent.Success(result.data))
                    else
                        _topAiringState.emit(AiringUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchRecentRelease(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.recentReleaseUseCase(page)) {
                is ResultState.Error -> {
                    _fetchState.emit(UIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchState.emit(UIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null) {
                        _fetchState.emit(UIEvent.Success(result.data))

                    } else
                        _fetchState.emit(UIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchPopular(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.popularAnimeUseCase(page)) {
                is ResultState.Error -> {
                    _fetchRecentAdded.emit(PopularUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchRecentAdded.emit(PopularUIEvent.Loading)
                }

                is ResultState.Success -> {

                    if (result.data != null)
                        _fetchRecentAdded.emit(PopularUIEvent.Success(result.data))
                    else
                        _fetchRecentAdded.emit(PopularUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchMovies(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.moviesAddedUseCase(page)) {
                is ResultState.Error -> {
                    _fetchCompletedAnime.emit(MovieAnimeUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchCompletedAnime.emit(MovieAnimeUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _fetchCompletedAnime.emit(MovieAnimeUIEvent.Success(result.data))
                    else
                        _fetchCompletedAnime.emit(MovieAnimeUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }
}
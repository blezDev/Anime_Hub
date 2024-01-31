package com.blez.anime_player_compose.feature_detail_info.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel
import com.blez.anime_player_compose.feature_detail_info.domain.use_cases.DetailsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val detailsUseCases: DetailsUseCases) : ViewModel() {

    sealed class DetailsUIEvent {
        data object Loading : DetailsUIEvent()
        data class Success(val data: AnimeInfoModel) : DetailsUIEvent()
        data class Failure(val message: String) : DetailsUIEvent()
    }

    private val _detailState = MutableStateFlow<DetailsUIEvent>(DetailsUIEvent.Loading)
    val detailState: StateFlow<DetailsUIEvent>
        get() = _detailState

    fun fetchDetails(animeId: String) {
        viewModelScope.launch {
            when (val result = detailsUseCases.infoDetails(animeId)) {
                is ResultState.Error -> {
                    _detailState.emit(DetailsUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _detailState.emit(DetailsUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _detailState.emit(DetailsUIEvent.Success(data = result.data))
                    else
                        _detailState.emit(DetailsUIEvent.Failure("Something went wrong"))
                }
            }
        }
    }
}











































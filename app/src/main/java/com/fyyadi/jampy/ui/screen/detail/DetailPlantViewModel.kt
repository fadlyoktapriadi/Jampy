package com.fyyadi.jampy.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlantViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase,
) : ViewModel() {

    private val _plantDetailState = MutableStateFlow<ResultState<Plant?>>(ResultState.Idle)
    val plantDetailState = _plantDetailState.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    fun getDetailPlant(id: Int) {
        viewModelScope.launch {
            _plantDetailState.value = ResultState.Loading
            coreUseCase.getDetailPlantUseCase(id)
                .collect { result ->
                    result
                        .onSuccess { plant ->
                            _plantDetailState.value = ResultState.Success(plant)
                        }
                        .onFailure { exception ->
                            _plantDetailState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun getBookmarkStatus(id: Int) {
        viewModelScope.launch {
            coreUseCase.isPlantBookmarkedUseCase(id).collect { bookmarked ->
                _isBookmarked.value = bookmarked
            }
        }
    }

    fun toggleBookmark(plant: Plant?) {
        plant ?: return
        viewModelScope.launch {
            try {
                if (_isBookmarked.value) {
                    coreUseCase.removeBookmarkPlantUseCase(plant.idPlant)
                } else {
                    coreUseCase.saveBookmarkPlantUseCase(plant)
                }
                _isBookmarked.value = !_isBookmarked.value
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
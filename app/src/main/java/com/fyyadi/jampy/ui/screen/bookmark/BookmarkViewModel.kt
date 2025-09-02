package com.fyyadi.jampy.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {

    private val _bookmarkPlantState = MutableStateFlow<ResultState<List<Plant>>>(ResultState.Idle)
    val bookmarkPlantState = _bookmarkPlantState.asStateFlow()

    fun getAllBookmarkPlants() {
        viewModelScope.launch {
            _bookmarkPlantState.value = ResultState.Loading
            coreUseCase.getAllBookmarkedPlantsUseCase()
                .collect { result ->
                    result
                        .onSuccess { plants ->
                            _bookmarkPlantState.value = ResultState.Success(plants)
                        }
                        .onFailure { exception ->
                            _bookmarkPlantState.value = ResultState.Error(exception.message)
                        }
                }
        }

    }

}
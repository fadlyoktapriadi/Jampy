package com.fyyadi.jampy.ui.screen.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.scan.domain.usecase.ScanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityHistoryViewModel @Inject constructor(
    private val scanUseCategory: ScanUseCase
) : ViewModel() {

    private val _activityHistoryState = MutableStateFlow<ResultState<List<HistoryScan>>>(ResultState.Idle)
    val activityHistoryState = _activityHistoryState.asStateFlow()

    fun getAllHistoryScanPlants() {
        viewModelScope.launch {
            _activityHistoryState.value = ResultState.Loading
            scanUseCategory.getAllHistoryScan()
                .collect { result ->
                    result
                        .onSuccess { plants ->
                            _activityHistoryState.value = ResultState.Success(plants)
                        }
                        .onFailure { exception ->
                            _activityHistoryState.value = ResultState.Error(exception.message)
                        }
                }
        }

    }

}
package com.fyyadi.management.presentation.ui.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.management.domain.usecase.ManagementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantManagementViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {

    private val _plantsState = MutableStateFlow<ResultState<List<Plant>>>(ResultState.Idle)
    val plantsState = _plantsState.asStateFlow()

    fun getAllPlants() {
        viewModelScope.launch {
            coreUseCase.getAllPlantUseCase()
                .collect { result ->
                    result
                        .onSuccess { plants ->
                            _plantsState.value = ResultState.Success(plants)
                        }
                        .onFailure { exception ->
                            _plantsState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

}
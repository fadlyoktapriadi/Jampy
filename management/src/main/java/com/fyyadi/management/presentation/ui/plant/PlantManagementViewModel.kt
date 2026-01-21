package com.fyyadi.management.presentation.ui.plant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.management.domain.model.AddPlant
import com.fyyadi.management.domain.usecase.ManagementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantManagementViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase,
    private val managementUseCase: ManagementUseCase
) : ViewModel() {

    private val _plantsState = MutableStateFlow<ResultState<List<Plant>>>(ResultState.Idle)
    val plantsState = _plantsState.asStateFlow()

    private val _addPlantState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val addPlantState = _addPlantState.asStateFlow()

    private val _plantDetailState = MutableStateFlow<ResultState<Plant?>>(ResultState.Idle)
    val plantDetailState = _plantDetailState.asStateFlow()

    private val _updatePlantState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val updatePlantState = _updatePlantState.asStateFlow()

    private val _deletePlantState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val deletePlantState = _deletePlantState.asStateFlow()

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

    fun addNewPlant(
        plantName: String,
        plantSpecies: String,
        plantDescription: String,
        healthBenefits: String,
        processingMethod: String,
        imagePlant: String
    ) {
        viewModelScope.launch {
            managementUseCase.addNewPlantUseCase(
                AddPlant(
                    plantName = plantName,
                    plantSpecies = plantSpecies,
                    plantDescription = plantDescription,
                    healthBenefits = healthBenefits,
                    processingMethod = processingMethod,
                    imagePlant = imagePlant
                )
            )
                .collect { result ->
                    result
                        .onSuccess {
                            _addPlantState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _addPlantState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun updatePlant(
        plant: Plant
    ) {
        viewModelScope.launch {
            managementUseCase.updatePlantUseCase(plant)
                .collect {
                    it
                        .onSuccess {
                            _updatePlantState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _updatePlantState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun deletePlant(plantId: Int) {
        viewModelScope.launch {
            managementUseCase.deletePlantUseCase(plantId)
                .collect {
                    it
                        .onSuccess {
                            _deletePlantState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _deletePlantState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun getDetailPlant(id: Int) {
        viewModelScope.launch {
            _plantDetailState.value = ResultState.Loading
            coreUseCase.getDetailPlantUseCase(id)
                .collect { result ->
                    result
                        .onSuccess { plant ->
                            _plantDetailState.value = ResultState.Success(plant)
                            Log.e("DetailPlantViewModel", "getDetailPlant: $plant" )
                        }
                        .onFailure { exception ->
                            _plantDetailState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }
}
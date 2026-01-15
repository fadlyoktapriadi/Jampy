package com.fyyadi.jampy.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {

    private val _profileUserState = MutableStateFlow<ResultState<UserProfile?>>(ResultState.Idle)
    val profileUserState = _profileUserState.asStateFlow()

    private val _plantHomeState = MutableStateFlow<ResultState<List<Plant>>>(ResultState.Idle)
    val plantHomeState = _plantHomeState.asStateFlow()

    private val _searchPlantState = MutableStateFlow<ResultState<List<Plant>>>(ResultState.Idle)
    val searchPlantState = _searchPlantState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val allPlants = MutableStateFlow<List<Plant>>(emptyList())

    private var debounceJob: Job? = null

    fun getUserProfile() {
        viewModelScope.launch {
            _profileUserState.value = ResultState.Loading
            coreUseCase.getUserProfileUseCase()
                .collect { result ->
                    result
                        .onSuccess { profile ->
                            _profileUserState.value = ResultState.Success(profile)
                        }
                        .onFailure { exception ->
                            _profileUserState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun getPlantHome() {
        viewModelScope.launch {
            _plantHomeState.value = ResultState.Loading
            coreUseCase.getPlantHomeUseCase()
                .collect { result ->
                    result
                        .onSuccess { plants ->
                            _plantHomeState.value = ResultState.Success(plants)
                        }
                        .onFailure { exception ->
                            _plantHomeState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun getPlants() {
        if (_searchPlantState.value is ResultState.Loading) return
        viewModelScope.launch {
            _searchPlantState.value = ResultState.Loading
            coreUseCase.getAllPlantUseCase()
                .collect { result ->
                    result
                        .onSuccess { plants ->
                            allPlants.value = plants
                            applyFilter(_searchQuery.value)
                        }
                        .onFailure { exception ->
                            _searchPlantState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(400L)
            applyFilter(newQuery)
        }
    }

    private fun applyFilter(query: String) {
        val source = allPlants.value
        if (source.isEmpty()) {
            return
        }
        if (query.isBlank()) {
            _searchPlantState.value = ResultState.Success(source)
            return
        }
        val q = query.trim().lowercase()
        val filtered = source.filter { plant ->
            val benefit = plant.healthBenefits.lowercase()
            plant.plantName.lowercase().contains(q) ||
                    plant.plantSpecies.lowercase().contains(q) ||
                    benefit.contains(q)
        }
        _searchPlantState.value = ResultState.Success(filtered)
    }
}
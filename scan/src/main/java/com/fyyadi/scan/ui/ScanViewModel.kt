package com.fyyadi.scan.ui

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.domain.repository.PlantClassificationRepository
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.scan.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase,
    private val plantClassificationRepository: PlantClassificationRepository
) : ViewModel() {

    private val _selectedImage = MutableStateFlow<Uri?>(null)
    val selectedImage = _selectedImage.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _classificationResults = MutableStateFlow<List<PlantLabel>>(emptyList())
    val classificationResults = _classificationResults.asStateFlow()

    private val _resultScan = MutableStateFlow<ResultState<Plant?>>(ResultState.Idle)
    val resultScan = _resultScan.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _isModelReady = MutableStateFlow(false)
    val isModelReady = _isModelReady.asStateFlow()

    init {
        downloadModel()
    }

    fun setImage(uri: Uri?) {
        _selectedImage.value = uri
        _classificationResults.value = emptyList()
        _errorMessage.value = null
    }

    fun setProcessing(v: Boolean) { _isProcessing.value = v }

    fun classifyImage(bitmap: Bitmap) {
        if (!plantClassificationRepository.isModelReady()) {
            _errorMessage.value = "Model is not ready. Please wait for download to complete."
            return
        }

        Log.e("ScanViewModel", "classifyImage: bitmap size ${bitmap.width}x${bitmap.height}" )

        viewModelScope.launch {
            _isProcessing.value = true
            _errorMessage.value = null

            plantClassificationRepository.classifyPlant(bitmap)
                .onSuccess { results ->
                    _classificationResults.value = results
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Classification failed"
                }

            _isProcessing.value = false
        }
    }

    private fun downloadModel() {
        viewModelScope.launch {
            plantClassificationRepository.downloadModel()
                .onSuccess {
                    _isModelReady.value = true
                }
                .onFailure { exception ->
                    _errorMessage.value = "Failed to download model: ${exception.message}"
                }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun getPlantResult(plantName: String) {
        viewModelScope.launch {
            coreUseCase.getPlantResultUseCase(plantName)
                .collect { result ->
                    result
                        .onSuccess { plant ->
                            Log.e("SCAN DI VIEWMODEL", "getPlantResult: $plant" )
                            _resultScan.value  = ResultState.Success(plant)
                        }
                        .onFailure { exception ->

                            _resultScan.value = ResultState.Error(exception.message)
                        }
                }
        }
    }
}
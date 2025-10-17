package com.fyyadi.scan.presentation.ui

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.domain.model.PlantLabel
import com.fyyadi.scan.domain.usecase.ScanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanUseCase: ScanUseCase,
) : ViewModel() {

    private val _selectedImage = MutableStateFlow<Uri?>(null)
    val selectedImage = _selectedImage.asStateFlow()

    private val _classificationResults = MutableStateFlow<ResultState<List<PlantLabel>>>(ResultState.Idle)
    val classificationResults = _classificationResults.asStateFlow()

    private val _detailResultClassify = MutableStateFlow<ResultState<Plant?>>(ResultState.Idle)
    val detailResultClassify = _detailResultClassify.asStateFlow()

    private val _isModelReady = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val isModelReady = _isModelReady.asStateFlow()


    init {
        downloadModel()
    }

    fun setImage(uri: Uri?) {
        _selectedImage.value = uri
        _classificationResults.value = ResultState.Idle
    }

    fun classifyImage(bitmap: Bitmap) {
        if (isModelReady.value != ResultState.Success(true)) {
            return
        }

        Log.e("ScanViewModel", "classifyImage: bitmap size ${bitmap.width}x${bitmap.height}" )

        viewModelScope.launch {
            scanUseCase.plantClassifyUseCase(bitmap)
                .onSuccess { data ->
                    _classificationResults.value = ResultState.Success(data)
                }
                .onFailure { exception ->
                    _classificationResults.value = ResultState.Error(exception.message)
                }
        }
    }

    private fun downloadModel() {
        viewModelScope.launch {
            scanUseCase.downloadModelUseCase()
                .onSuccess {
                    _isModelReady.value = ResultState.Success(true)
                }
                .onFailure { exception ->
                    _isModelReady.value = ResultState.Error(exception.message)
                }
        }
    }

    fun getDetailResultClassify(plantName: String) {
        viewModelScope.launch {
            scanUseCase.getDetailResultClassifyUseCase(plantName)
                .collect { result ->
                    result
                        .onSuccess { plant ->
                            _detailResultClassify.value  = ResultState.Success(plant)
                        }
                        .onFailure { exception ->
                            _detailResultClassify.value = ResultState.Error(exception.message)
                        }
                }
        }
    }
}
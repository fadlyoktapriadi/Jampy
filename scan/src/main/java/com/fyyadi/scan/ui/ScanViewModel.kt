package com.fyyadi.scan.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.fyyadi.domain.usecase.CoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {
    private val _selectedImage = MutableStateFlow<Uri?>(null)
    val selectedImage = _selectedImage.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    fun setImage(uri: Uri?) { _selectedImage.value = uri }
    fun setProcessing(v: Boolean) { _isProcessing.value = v }
}
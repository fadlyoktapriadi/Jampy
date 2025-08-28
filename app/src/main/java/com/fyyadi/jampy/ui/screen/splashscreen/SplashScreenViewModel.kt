package com.fyyadi.jampy.ui.screen.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {

    private val _loggedState = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val loggedState = _loggedState.asStateFlow()

    fun checkUserLogin() {
        viewModelScope.launch {
            _loggedState.value = ResultState.Loading
            coreUseCase.getLoginStatusUseCase()
                .collect { result ->
                    result
                        .onSuccess { isLoggedIn ->
                            if (isLoggedIn) {
                                _loggedState.value = ResultState.Success(true)
                            } else {
                                _loggedState.value = ResultState.Error("User not logged in")
                            }
                        }
                        .onFailure { exception ->
                            _loggedState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }


}

package com.fyyadi.jampy.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.auth.domain.usecase.AuthUseCase
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _profileUserState = MutableStateFlow<ResultState<UserProfile?>>(ResultState.Idle)
    val profileUserState = _profileUserState.asStateFlow()

    private val _logoutState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val logoutState = _logoutState.asStateFlow()

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

    fun logout() {
        viewModelScope.launch {
            authUseCase.logoutUseCase()
                .collect { result ->
                    result
                        .onSuccess {
                            _logoutState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _logoutState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }
}
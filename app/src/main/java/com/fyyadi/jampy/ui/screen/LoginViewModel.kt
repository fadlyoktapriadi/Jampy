package com.fyyadi.jampy.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.core.domain.model.UserProfile
import com.fyyadi.core.domain.usecase.CoreUseCase
import com.fyyadi.core.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel() {

    private val _loginAuthState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val loginAuthState = _loginAuthState.asStateFlow()

    private val _addUserState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val addUserState = _addUserState.asStateFlow()

    private val _checkUserLoginState = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val checkUserLoginState = _checkUserLoginState.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginAuthState.value = ResultState.Loading
            coreUseCase.authUseCase(idToken).collect { result ->
                result
                    .onSuccess {
                        _loginAuthState.value = ResultState.Success(Unit)
                    }
                    .onFailure { exception ->
                        _loginAuthState.value = ResultState.Error(exception.message)
                    }
            }
        }
    }

    fun updateUserProfile(profile: UserProfile) {
        _userProfile.value = profile
    }

    fun checkUserLogin() {
        val profile = _userProfile.value ?: return
        viewModelScope.launch {
            coreUseCase.checkUserLoginUseCase(profile)
                .collect { result ->
                result
                    .onSuccess { isLoggedIn ->
                        if (isLoggedIn) {
                            _checkUserLoginState.value = ResultState.Success(true)
                        } else {
                            _checkUserLoginState.value = ResultState.Success(false)
                        }
                    }
                    .onFailure { exception ->
                        _checkUserLoginState.value = ResultState.Error(exception.message)
                    }
            }
        }
    }

    fun addUser() {
        val profile = _userProfile.value ?: return
        viewModelScope.launch {
            coreUseCase.addUserUseCase(profile)
                .collect { result ->
                result
                    .onSuccess {
                        _addUserState.value = ResultState.Success(Unit)
                    }
                    .onFailure { exception ->
                        _addUserState.value = ResultState.Error(exception.message)
                    }
            }
        }
    }
}
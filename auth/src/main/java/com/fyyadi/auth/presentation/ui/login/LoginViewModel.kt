package com.fyyadi.auth.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.auth.domain.usecase.AuthUseCase
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
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
            authUseCase.signInWithGoogleAuthUseCase(idToken).collect { result ->
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
            authUseCase.checkUserLoginUseCase(profile)
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
            authUseCase.addUserUseCase(profile)
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

    fun saveLoginSessionUser() {
        val profile = _userProfile.value ?: return
        viewModelScope.launch {
            authUseCase.saveSessionLoginUseCase(profile)
                .collect { result ->
                    result
                        .onSuccess {
                        }
                        .onFailure { exception ->
                            Log.e("LoginViewModel", "saveUserLogin: ${exception.message}" )
                        }
                }
        }
    }
}
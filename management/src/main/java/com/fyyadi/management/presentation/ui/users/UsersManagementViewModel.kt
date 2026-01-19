package com.fyyadi.management.presentation.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.domain.usecase.ManagementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersManagementViewModel @Inject constructor(
    private val manageUseCase: ManagementUseCase
) : ViewModel() {

    private val _usersState = MutableStateFlow<ResultState<List<UserProfile>>>(ResultState.Idle)
    val usersState = _usersState.asStateFlow()

    private val _updateRoleState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val updateRoleState = _updateRoleState.asStateFlow()

    private val _deleteUserState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val deleteUserState = _deleteUserState.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch {
            manageUseCase.getAllUsersUseCase()
                .collect { result ->
                    result
                        .onSuccess { users ->
                            _usersState.value = ResultState.Success(users)
                        }
                        .onFailure { exception ->
                            _usersState.value = ResultState.Error(exception.message)
                        }
                }
        }
    }

    fun updateUserRole(
        userId: Int,
        newRole: String
    ) {
        viewModelScope.launch {
            manageUseCase.updateUserRoleUseCase(userId, newRole)
                .collect { result ->
                    result
                        .onSuccess {
                            _updateRoleState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _updateRoleState.value = ResultState.Error(exception.message)
                        }
                }

        }
    }

    fun deleteUser(
        userId: Int
    ) {
        viewModelScope.launch {
            manageUseCase.deleteUserUseCase(userId)
                .collect { result ->
                    result
                        .onSuccess {
                            _deleteUserState.value = ResultState.Success(Unit)
                        }
                        .onFailure { exception ->
                            _deleteUserState.value = ResultState.Error(exception.message)
                        }

                }

        }
    }

}
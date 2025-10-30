package com.fyyadi.auth.domain.usecase

import com.fyyadi.auth.domain.repository.AuthRepository
import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(user: UserProfile): Flow<Result<Unit>> =
        authRepository.addUser(user)
}
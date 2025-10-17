package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(user: UserProfile): Flow<Result<Unit>> =
        authRepository.addUser(user)
}
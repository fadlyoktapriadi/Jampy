package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(idToken: String): Flow<Result<Unit>> =
        authRepository.signInWithGoogle(idToken)
}
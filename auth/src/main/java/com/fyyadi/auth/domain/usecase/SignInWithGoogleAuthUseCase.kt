package com.fyyadi.auth.domain.usecase

import com.fyyadi.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(idToken: String): Flow<Result<Unit>> =
        authRepository.signInWithGoogle(idToken)
}
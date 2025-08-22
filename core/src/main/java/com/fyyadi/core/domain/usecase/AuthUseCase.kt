package com.fyyadi.core.domain.usecase

import com.fyyadi.core.domain.model.User
import com.fyyadi.core.domain.repository.AuthRepository
import com.fyyadi.core.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(idToken: String) {
        authRepository.signInWithGoogle(idToken)
    }
}
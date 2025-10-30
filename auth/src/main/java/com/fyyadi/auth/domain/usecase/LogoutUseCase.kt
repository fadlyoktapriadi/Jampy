package com.fyyadi.auth.domain.usecase

import com.fyyadi.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Result<Unit>> = authRepository.logout()
}
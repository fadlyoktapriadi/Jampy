package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Result<Unit>> = authRepository.logout()
}
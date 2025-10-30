package com.fyyadi.auth.domain.usecase

import com.fyyadi.auth.domain.repository.AuthRepository
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSessionLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(user: UserProfile): Flow<Result<Unit>> =
        authRepository.saveSessionLogin(user)
}
package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.AuthRepository
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(user: UserProfile): Flow<Result<Boolean>> =
        authRepository.checkUserLogin(user)
}
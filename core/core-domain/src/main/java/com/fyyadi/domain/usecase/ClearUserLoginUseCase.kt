package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClearUserLoginUseCase @Inject constructor(
    private val coreRepository: CoreRepository
) {
    operator fun invoke(): Flow<Result<Unit>> =
        coreRepository.clearUserLogin()
}
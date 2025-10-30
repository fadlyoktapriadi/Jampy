package com.fyyadi.auth.domain.usecase

import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val coreRepository: CoreRepository
) {
    operator fun invoke(): Flow<Result<Boolean>> =
        coreRepository.isUserLoggedIn()
}
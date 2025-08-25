package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val coreRepository: CoreRepository
){
    operator fun invoke(idToken: String): Flow<Result<Unit>> =
        coreRepository.signInWithGoogle(idToken)
}
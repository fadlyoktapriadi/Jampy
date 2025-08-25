package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserLoginUseCase @Inject constructor(
    private val coreRepository: CoreRepository
){
    operator fun invoke(user: UserProfile): Flow<Result<Unit>> =
        coreRepository.addUser(user)
}
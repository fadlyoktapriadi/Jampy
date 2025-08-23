package com.fyyadi.core.domain.usecase

import com.fyyadi.core.domain.model.UserProfile
import com.fyyadi.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val coreRepository: CoreRepository
){
    operator fun invoke(user: UserProfile): Flow<Result<Unit>> =
        coreRepository.addUser(user)
}
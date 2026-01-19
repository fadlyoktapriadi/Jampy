package com.fyyadi.management.domain.usecase

import com.fyyadi.management.domain.repository.ManagementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val managementRepository: ManagementRepository
) {
    operator fun invoke(userId: Int): Flow<Result<Unit>> =
        managementRepository.deleteUser(userId)
}
package com.fyyadi.management.domain.usecase

import com.fyyadi.management.domain.repository.ManagementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserRoleUseCase @Inject constructor(
    private val managementRepository: ManagementRepository
) {
    operator fun invoke(
        userId: Int,
        newRole: String
    ): Flow<Result<Unit>> =
        managementRepository.updateUserRole(userId, newRole)
}
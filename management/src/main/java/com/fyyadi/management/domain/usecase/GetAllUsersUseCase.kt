package com.fyyadi.management.domain.usecase

import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.domain.repository.ManagementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val managementRepository: ManagementRepository
) {
    operator fun invoke(): Flow<Result<List<UserProfile>>> =
        managementRepository.getAllUsers()
}
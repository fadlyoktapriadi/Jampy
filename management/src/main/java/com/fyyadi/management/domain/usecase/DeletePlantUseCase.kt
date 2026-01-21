package com.fyyadi.management.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.management.domain.repository.ManagementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DeletePlantUseCase @Inject constructor(
    private val repository: ManagementRepository
) {
    operator fun invoke(
        idPlant: Int
    ): Flow<Result<Unit>> {
        return repository.deletePlant(idPlant)
    }
}
package com.fyyadi.management.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.management.domain.model.AddPlant
import com.fyyadi.management.domain.repository.ManagementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePlantUseCase @Inject constructor(
    private val repository: ManagementRepository
) {
    operator fun invoke(
        plant: Plant
    ): Flow<Result<Unit>> {
        return repository.updatePlant(plant)
    }
}
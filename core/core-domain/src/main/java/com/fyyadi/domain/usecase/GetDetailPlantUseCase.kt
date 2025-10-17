package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.CoreRepository
import com.fyyadi.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailPlantUseCase @Inject constructor(
    private val plantRepository: PlantRepository
) {
    operator fun invoke(plantId: Int): Flow<Result<Plant?>> =
        plantRepository.getDetailPlant(plantId)
}

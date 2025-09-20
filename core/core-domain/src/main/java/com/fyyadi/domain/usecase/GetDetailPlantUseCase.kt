package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailPlantUseCase @Inject constructor(
    private val coreRepository: CoreRepository
) {
    operator fun invoke(plantId: Int): Flow<Result<Plant?>> =
        coreRepository.getDetailPlant(plantId)
}

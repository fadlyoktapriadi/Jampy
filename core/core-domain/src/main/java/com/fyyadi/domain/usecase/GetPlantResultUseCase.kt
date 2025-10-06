package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantResultUseCase @Inject constructor(
    private val coreRepository: CoreRepository
) {

    operator fun invoke(plantName: String): Flow<Result<Plant>> =
        coreRepository.getPlantResult(plantName)
}

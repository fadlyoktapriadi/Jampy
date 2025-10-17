package com.fyyadi.scan.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailResultClassifyUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    operator fun invoke(plantName: String): Flow<Result<Plant>> =
        plantClassificationRepository.getDetailResultClassify(plantName)
}

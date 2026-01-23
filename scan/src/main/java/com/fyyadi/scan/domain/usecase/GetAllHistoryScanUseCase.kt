package com.fyyadi.scan.domain.usecase

import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHistoryScanUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    operator fun invoke(): Flow<Result<List<HistoryScan>>> =
        plantClassificationRepository.getAllHistoryScan()
}

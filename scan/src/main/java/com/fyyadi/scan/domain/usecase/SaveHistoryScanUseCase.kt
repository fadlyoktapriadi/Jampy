package com.fyyadi.scan.domain.usecase

import com.fyyadi.scan.domain.model.SaveHistoryScanRequest
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveHistoryScanUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    operator fun invoke(
        userEmail: String,
        plantId: Int,
        accuracy: Int,
    ): Flow<Result<Unit>> {

        val saveHistoryScanRequest = SaveHistoryScanRequest(
            userEmail = userEmail,
            plantId = plantId,
            accuracy = accuracy
        )

        return plantClassificationRepository.saveHistoryScan(saveHistoryScanRequest)
    }
}

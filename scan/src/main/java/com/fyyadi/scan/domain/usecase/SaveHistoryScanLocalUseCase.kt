package com.fyyadi.scan.domain.usecase

import com.fyyadi.scan.domain.model.HistoryScanLocal
import com.fyyadi.scan.domain.model.SaveHistoryScanRequest
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveHistoryScanLocalUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    operator fun invoke(
        userEmail: String,
        plantId: Int,
        imageResultUri: String,
        accuracy: Int
    ): Flow<Result<Unit>> {
        val saveHistoryLocal = HistoryScanLocal(
            userEmail = userEmail,
            plantId = plantId,
            imageResultUri = imageResultUri,
            accuracy = accuracy
        )
        return plantClassificationRepository.saveHistoryScanLocal(saveHistoryLocal)
    }
}

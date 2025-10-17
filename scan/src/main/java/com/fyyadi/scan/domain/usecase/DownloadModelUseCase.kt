package com.fyyadi.scan.domain.usecase

import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import javax.inject.Inject

class DownloadModelUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    suspend operator fun invoke(): Result<Unit> =
        plantClassificationRepository.downloadModel()
}

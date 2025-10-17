package com.fyyadi.scan.domain.usecase

import android.graphics.Bitmap
import com.fyyadi.scan.domain.model.PlantLabel
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import javax.inject.Inject

class PlantClassifyUseCase @Inject constructor(
    private val plantClassificationRepository: PlantClassificationRepository
) {
    suspend operator fun invoke(plantBitmap: Bitmap): Result<List<PlantLabel>> =
        plantClassificationRepository.classifyPlant(plantBitmap)
}

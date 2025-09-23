package com.fyyadi.domain.repository

import android.graphics.Bitmap
import com.fyyadi.domain.model.PlantLabel

interface PlantClassificationRepository {
    suspend fun classifyPlant(bitmap: Bitmap): Result<List<PlantLabel>>
    suspend fun downloadModel(): Result<Unit>
    fun isModelReady(): Boolean
}
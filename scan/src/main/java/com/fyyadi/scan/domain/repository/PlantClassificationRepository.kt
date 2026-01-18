package com.fyyadi.scan.domain.repository

import android.graphics.Bitmap
import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.scan.domain.model.HistoryScanLocal
import com.fyyadi.scan.domain.model.PlantLabel
import com.fyyadi.scan.domain.model.SaveHistoryScanRequest
import kotlinx.coroutines.flow.Flow

interface PlantClassificationRepository {
    suspend fun classifyPlant(bitmap: Bitmap): Result<List<PlantLabel>>
    suspend fun downloadModel(): Result<Unit>
    fun isModelReady(): Boolean
    fun getDetailResultClassify(plantName: String): Flow<Result<Plant>>
    fun saveHistoryScan(
        saveHistoryScanRequest: SaveHistoryScanRequest
    ): Flow<Result<Unit>>
    fun saveHistoryScanLocal(history: HistoryScanLocal): Flow<Result<Unit>>

    fun getAllHistoryScan(): Flow<Result<List<HistoryScan>>>
}
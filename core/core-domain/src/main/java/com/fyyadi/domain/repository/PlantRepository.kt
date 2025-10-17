package com.fyyadi.domain.repository

import com.fyyadi.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getPlantHome(): Flow<Result<List<Plant>>>
    fun getAllPlants(): Flow<Result<List<Plant>>>
    fun getDetailPlant(plantId: Int): Flow<Result<Plant?>>
}
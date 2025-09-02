package com.fyyadi.domain.repository

import com.fyyadi.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun savePlant(plant: Plant)
    suspend fun removePlant(id: Int)
    fun getAllPlant(): Flow<List<Plant>>
    fun isBookmarked(id: Int): Flow<Boolean>
}
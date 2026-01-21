package com.fyyadi.management.domain.repository

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.domain.model.AddPlant
import kotlinx.coroutines.flow.Flow

interface ManagementRepository {

    fun getAllUsers(): Flow<Result<List<UserProfile>>>
    fun updateUserRole(userId: Int, newRole: String): Flow<Result<Unit>>
    fun deleteUser(userId: Int): Flow<Result<Unit>>
    fun addNewPlant(plant: AddPlant): Flow<Result<Unit>>
    fun updatePlant(plant: Plant): Flow<Result<Unit>>
    fun deletePlant(plantId: Int): Flow<Result<Unit>>
}
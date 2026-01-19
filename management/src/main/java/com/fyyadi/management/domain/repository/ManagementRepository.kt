package com.fyyadi.management.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ManagementRepository {

    fun getAllUsers(): Flow<Result<List<UserProfile>>>
    fun updateUserRole(userId: Int, newRole: String): Flow<Result<Unit>>

    fun deleteUser(userId: Int): Flow<Result<Unit>>
}
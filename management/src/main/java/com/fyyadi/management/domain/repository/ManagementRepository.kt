package com.fyyadi.management.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ManagementRepository {

    fun getAllUsers(): Flow<Result<List<UserProfile>>>

}
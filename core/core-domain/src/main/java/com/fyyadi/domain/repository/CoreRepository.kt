package com.fyyadi.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface CoreRepository {

    fun saveUserLogin(user: UserProfile): Flow<Result<Unit>>
    fun clearUserLogin(): Flow<Result<Unit>>
    fun isUserLoggedIn(): Flow<Result<Boolean>>
    fun getUserProfile(): Flow<Result<UserProfile?>>

}
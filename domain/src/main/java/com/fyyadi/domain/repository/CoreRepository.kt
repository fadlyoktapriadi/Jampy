package com.fyyadi.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface CoreRepository {
    // Auth
    fun signInWithGoogle(idToken: String) : Flow<Result<Unit>>
    fun addUser(user: UserProfile) : Flow<Result<Unit>>
    fun checkUserLogin(user: UserProfile): Flow<Result<Boolean>>

    // Preferences
    fun saveUserLogin(user: UserProfile): Flow<Result<Unit>>
    fun clearUserLogin(): Flow<Result<Unit>>
    fun isUserLoggedIn(): Flow<Result<Boolean>>
    fun getUserProfile(): Flow<Result<UserProfile?>>
}
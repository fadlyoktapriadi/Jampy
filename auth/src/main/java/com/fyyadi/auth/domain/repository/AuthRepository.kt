package com.fyyadi.auth.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signInWithGoogle(idToken: String) : Flow<Result<Unit>>
    fun addUser(user: UserProfile) : Flow<Result<Unit>>
    fun checkUserLogin(user: UserProfile): Flow<Result<Boolean>>
    fun saveSessionLogin(user: UserProfile): Flow<Result<Unit>>
    fun getProfileUser(email: String): Flow<Result<UserProfile?>>
    fun logout(): Flow<Result<Unit>>

}
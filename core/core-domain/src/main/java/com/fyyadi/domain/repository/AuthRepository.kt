package com.fyyadi.domain.repository

import com.fyyadi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signInWithGoogle(idToken: String) : Flow<Result<Unit>>
    fun addUser(user: UserProfile) : Flow<Result<Unit>>
    fun checkUserLogin(user: UserProfile): Flow<Result<Boolean>>
    fun getProfileUser(email: String): Flow<Result<UserProfile?>>

}
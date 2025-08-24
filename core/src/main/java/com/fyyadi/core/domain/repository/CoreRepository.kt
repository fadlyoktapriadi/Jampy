package com.fyyadi.core.domain.repository

import com.fyyadi.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface CoreRepository {
    fun signInWithGoogle(idToken: String) : Flow<Result<Unit>>
    fun addUser(user: UserProfile) : Flow<Result<Unit>>
    fun checkUserLogin(user: UserProfile): Flow<Result<Boolean>>
}
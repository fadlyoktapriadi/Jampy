package com.fyyadi.core.domain.repository

import com.fyyadi.core.domain.model.User
import com.fyyadi.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String)
}
package com.fyyadi.data.repository

import android.util.Log
import com.fyyadi.data.mapper.toProfileUser
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val postgrest: Postgrest
) : CoreRepository {

    override fun saveUserLogin(user: UserProfile): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn = true
            preferenceManager.userEmail = user.userEmail
        }
        emit(result)
    }

    override fun clearUserLogin(): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.clearUserData()
        }
        emit(result)
    }

    override fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn
        }
        emit(result)
    }

    override fun getUserProfile(): Flow<Result<UserProfile?>> = flow {
        val result = runCatching {
            val email = preferenceManager.userEmail ?: return@runCatching null
            val response = postgrest.from("users")
                .select {
                    filter {
                        eq("user_email", email)
                    }
                }
                .decodeList<UserProfileDto>()
            response.firstOrNull()?.toProfileUser()
        }
        emit(result)
    }
}
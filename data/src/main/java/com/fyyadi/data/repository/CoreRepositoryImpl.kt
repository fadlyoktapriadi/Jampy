package com.fyyadi.data.repository

import android.util.Log
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest,
    private val preferenceManager: PreferenceManager
) : CoreRepository {
    // Auth
    override fun signInWithGoogle(idToken: String): Flow<Result<Unit>> = flow {
        val result = runCatching {
            auth.signInWith(IDToken) {
                this.idToken = idToken
                provider = Google
            }
        }
        emit(result)
    }

    override fun addUser(user: UserProfile): Flow<Result<Unit>> = flow {
        val result = runCatching {
            val userDto = mapOf(
                "fullName" to user.fullName,
                "email" to user.email,
                "avatar_url" to user.avatarUrl,
                "role" to user.role
            )
            postgrest.from("users").upsert(userDto)
            Unit
        }
        emit(result)
    }

    override fun checkUserLogin(user: UserProfile): Flow<Result<Boolean>> = flow {
        val result = runCatching {
            val response = postgrest.from("users")
                .select {
                    filter {
                        eq("email", user.email ?: "")
                    }
                }
                .decodeList<UserProfileDto>()
            Log.e("CoreRepositoryImpl", "checkUserLogin: $response")
            response.isNotEmpty()
        }
        emit(result)
    }

    // Preferences
    override fun saveUserLogin(user: UserProfile): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn = true
            preferenceManager.userEmail = user.email
            preferenceManager.userFullName = user.fullName
            preferenceManager.userAvatarUrl = user.avatarUrl
            preferenceManager.userRole = user.role
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
            if (preferenceManager.isLoggedIn) {
                UserProfile(
                    id = null,
                    fullName = preferenceManager.userFullName,
                    email = preferenceManager.userEmail,
                    avatarUrl = preferenceManager.userAvatarUrl,
                    role = preferenceManager.userRole
                )
            } else null
        }
        emit(result)
    }

}
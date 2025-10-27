package com.fyyadi.data.repository

import android.util.Log
import com.fyyadi.data.mapper.toProfileUser
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest,
    private val preferenceManager: PreferenceManager
) : AuthRepository {

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
                "user_full_name" to user.userFullName,
                "user_email" to user.userEmail,
                "photo_profile" to user.photoProfile,
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
                        eq("user_email", user.userEmail ?: "")
                    }
                }
                .decodeList<UserProfileDto>()
            response.isNotEmpty()
        }
        emit(result)
    }

    override fun getProfileUser(email: String): Flow<Result<UserProfile?>> = flow {
        val result = runCatching {
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

    override fun logout(): Flow<Result<Unit>> {
        return flow {
            val result = runCatching {
                preferenceManager.clearUserData()
                auth.signOut()
                Log.e("AuthRepositoryImpl", "User logged out successfully" )
                Unit
            }
            emit(result)
        }
    }

}
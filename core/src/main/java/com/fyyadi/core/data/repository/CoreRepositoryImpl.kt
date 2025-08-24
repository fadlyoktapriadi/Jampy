package com.fyyadi.core.data.repository

import android.util.Log
import com.fyyadi.core.data.remote.dto.UserProfileDto
import com.fyyadi.core.domain.model.UserProfile
import com.fyyadi.core.domain.repository.CoreRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : CoreRepository {

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

}
package com.fyyadi.data.repository

import com.fyyadi.data.mapper.toPlant
import com.fyyadi.data.mapper.toProfileUser
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.map

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

    // Preferences
    override fun saveUserLogin(user: UserProfile): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn = true
            preferenceManager.userEmail = user.userEmail
            preferenceManager.userFullName = user.userFullName
            preferenceManager.userPhotoProfileUrl = user.photoProfile
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
                    userId = null,
                    userFullName = preferenceManager.userFullName,
                    userEmail = preferenceManager.userEmail,
                    photoProfile = preferenceManager.userPhotoProfileUrl,
                    role = preferenceManager.userRole
                )
            } else null
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

    override fun getPlantHome(): Flow<Result<List<Plant>>> = flow {
        val result = runCatching {
            val response = postgrest.from("herb_plants")
                .select{
                    limit(4)
                }
                .decodeList<PlantDto>()
            response.map { it.toPlant() }
        }
        emit(result)
    }

    override fun getAllPlants(): Flow<Result<List<Plant>>> = flow {
        val result = runCatching {
            val response = postgrest.from("herb_plants")
                .select()
                .decodeList<PlantDto>()
            response.map { it.toPlant() }
        }
        emit(result)
    }

    override fun getDetailPlant(plantId: Int): Flow<Result<Plant?>> {
        return flow {
            val result = runCatching {
                val response = postgrest.from("herb_plants")
                    .select {
                        filter {
                            eq("plant_id", plantId)
                        }
                    }
                    .decodeList<PlantDto>()
                response.firstOrNull()?.toPlant()
            }
            emit(result)
        }
    }

    override fun getPlantResult(plantName: String): Flow<Result<Plant>> {
        return flow {
            val result = runCatching {
                val response = postgrest.from("herb_plants")
                    .select {
                        filter {
                            eq("plant_name", plantName)
                        }
                        limit(1)
                    }
                    .decodeList<PlantDto>()
                response.first().toPlant()
            }
            emit(result)
        }
    }
}
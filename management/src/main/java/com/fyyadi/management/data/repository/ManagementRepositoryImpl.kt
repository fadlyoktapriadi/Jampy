package com.fyyadi.management.data.repository

import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.data.mapper.toUserProfileList
import com.fyyadi.management.domain.repository.ManagementRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ManagementRepository {

    override fun getAllUsers(): Flow<Result<List<UserProfile>>> {
        return flow {
            val result = runCatching {
                val response = postgrest.from("users")
                    .select {
                        filter {
                            neq("role", "Administrator")
                        }
                    }
                    .decodeList<UserProfileDto>()
                response.toUserProfileList()
            }
            emit(result)
        }
    }

    override fun updateUserRole(userId: Int, newRole: String): Flow<Result<Unit>> {
        return flow {
            val result = runCatching {
                postgrest.from("users")
                    .update(mapOf("role" to newRole)) {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                Unit
            }
            emit(result)
        }
    }

    override fun deleteUser(userId: Int): Flow<Result<Unit>> {
        return flow {
            val result = runCatching {
                postgrest.from("users")
                    .delete {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                Unit
            }
            emit(result)
        }
    }
}

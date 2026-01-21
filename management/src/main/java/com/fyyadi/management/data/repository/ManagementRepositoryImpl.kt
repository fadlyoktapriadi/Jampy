package com.fyyadi.management.data.repository

import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.data.mapper.toUserProfileList
import com.fyyadi.management.domain.model.AddPlant
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

    override fun addNewPlant(plant: AddPlant): Flow<Result<Unit>> = flow {
        val result = runCatching {
            val addPlantDto = mapOf(
                "plant_name" to plant.plantName,
                "plant_species" to plant.plantSpecies,
                "plant_description" to plant.plantDescription,
                "health_benefits" to plant.healthBenefits,
                "processing_method" to plant.processingMethod,
                "image_plant" to plant.imagePlant
            )
            postgrest.from("herb_plants").insert(addPlantDto)
            Unit
        }
        emit(result)
    }

    override fun updatePlant(plant: Plant): Flow<Result<Unit>> {
        return flow {
            val result = runCatching {
                val updatePlantDto = mapOf(
                    "plant_name" to plant.plantName,
                    "plant_species" to plant.plantSpecies,
                    "plant_description" to plant.plantDescription,
                    "health_benefits" to plant.healthBenefits,
                    "processing_method" to plant.processingMethod,
                    "image_plant" to plant.imagePlant
                )
                postgrest.from("herb_plants")
                    .update(updatePlantDto) {
                        filter {
                            eq("plant_id", plant.idPlant)
                        }
                    }
                Unit
            }
            emit(result)
        }
    }

    override fun deletePlant(plantId: Int): Flow<Result<Unit>> {
        return flow {
            val result = runCatching {
                postgrest.from("herb_plants")
                    .delete {
                        filter {
                            eq("plant_id", plantId)
                        }
                    }
                Unit
            }
            emit(result)
        }
    }
}

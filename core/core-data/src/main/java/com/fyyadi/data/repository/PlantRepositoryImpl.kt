package com.fyyadi.data.repository

import com.fyyadi.data.mapper.toPlant
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.PlantRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : PlantRepository {

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

}
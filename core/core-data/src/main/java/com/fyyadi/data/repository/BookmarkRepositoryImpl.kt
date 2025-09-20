package com.fyyadi.data.repository

import com.fyyadi.data.mapper.toDomain
import com.fyyadi.data.mapper.toEntity
import com.fyyadi.data.source.local.room.dao.PlantBookmarkDao
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dao: PlantBookmarkDao
) : BookmarkRepository {
    override suspend fun savePlant(plant: Plant) = dao.insert(plant.toEntity())
    override suspend fun removePlant(id: Int) = dao.deleteById(id)
    override fun getAllPlant(): Flow<Result<List<Plant>>> =
        dao.getAll()
            .map { list -> Result.success(list.map { it.toDomain() }) }
            .catch { emit(Result.failure(it)) }
    override fun isBookmarked(id: Int): Flow<Boolean> = dao.isBookmarked(id)
}
package com.fyyadi.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fyyadi.data.source.local.room.entity.PlantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantBookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PlantEntity)

    @Query("SELECT * FROM plant_bookmarks ORDER BY plantName")
    fun getAll(): Flow<List<PlantEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM plant_bookmarks WHERE idPlant = :id)")
    fun isBookmarked(id: Int): Flow<Boolean>

    @Query("SELECT * FROM plant_bookmarks WHERE idPlant = :id LIMIT 1")
    suspend fun getById(id: Int): PlantEntity?

    @Query("DELETE FROM plant_bookmarks WHERE idPlant = :id")
    suspend fun deleteById(id: Int)
}
package com.fyyadi.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fyyadi.data.source.local.room.entity.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun saveScanHistory(entity: ScanHistoryEntity)

    @Query("SELECT * FROM scan_history ORDER BY scanDate DESC")
    fun getAllHistory(): Flow<List<ScanHistoryEntity>>

    @Query("SELECT * FROM scan_history WHERE userEmail = :email ORDER BY scanDate DESC")
    fun getByUser(email: String): Flow<List<ScanHistoryEntity>>

    @Query("DELETE FROM scan_history WHERE idHistory = :id")
    suspend fun deleteById(id: Int)
}
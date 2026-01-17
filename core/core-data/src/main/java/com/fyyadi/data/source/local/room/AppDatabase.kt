package com.fyyadi.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fyyadi.data.source.local.room.dao.PlantBookmarkDao
import com.fyyadi.data.source.local.room.dao.ScanHistoryDao
import com.fyyadi.data.source.local.room.entity.PlantEntity
import com.fyyadi.data.source.local.room.entity.ScanHistoryEntity

@Database(
    entities = [
        PlantEntity::class,
        ScanHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantBookmarkDao(): PlantBookmarkDao
    abstract fun scanHistoryDao(): ScanHistoryDao

}
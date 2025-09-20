package com.fyyadi.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fyyadi.data.source.local.room.dao.PlantBookmarkDao
import com.fyyadi.data.source.local.room.entity.PlantEntity

@Database(
    entities = [PlantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantBookmarkDao(): PlantBookmarkDao
}
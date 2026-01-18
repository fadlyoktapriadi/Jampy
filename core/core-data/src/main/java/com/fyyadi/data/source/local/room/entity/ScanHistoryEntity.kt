package com.fyyadi.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true) val idHistory: Int = 0,
    val userEmail: String,
    val plantId: Int,
    val plantNamePredict: String,
    val imageResultUri: String,
    val accuracy: Int,
    val scanDate: Long = System.currentTimeMillis()
)
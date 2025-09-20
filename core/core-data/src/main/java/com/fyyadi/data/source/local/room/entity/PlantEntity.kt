package com.fyyadi.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_bookmarks")
data class PlantEntity (
    @PrimaryKey val idPlant: Int,
    val plantName: String,
    val plantSpecies: String,
    val plantDescription: String,
    val healthBenefits: String,
    val processingMethod: String,
    val imagePlant: String,
)
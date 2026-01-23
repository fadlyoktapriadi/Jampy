package com.fyyadi.scan.data.mapper

import com.fyyadi.data.source.local.room.entity.ScanHistoryEntity
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.scan.domain.model.HistoryScanLocal

fun PlantDto.toPlant(): Plant =
    Plant(
        idPlant = this.idPlant ?: 0,
        plantName = this.name.orEmpty(),
        plantSpecies = this.plantSpecies.orEmpty(),
        plantDescription = this.plantDescription.orEmpty(),
        healthBenefits = this.healthBenefits.orEmpty(),
        processingMethod = this.processingMethod.orEmpty(),
        imagePlant = this.imagePlant.orEmpty()
    )

fun HistoryScanLocal.toEntity(): ScanHistoryEntity =
    ScanHistoryEntity(
        userEmail = userEmail,
        plantId = plantId,
        plantNamePredict = plantNamePredict,
        imageResultUri = imageResultUri,
        accuracy = accuracy,
    )

fun ScanHistoryEntity.toDomain(): HistoryScan =
    HistoryScan(
        plantId = plantId,
        plantNamePredict = plantNamePredict,
        imageResultUri = imageResultUri,
        accuracy = accuracy,
        date = scanDate
    )
package com.fyyadi.scan.data.mapper

import com.fyyadi.data.source.local.room.entity.ScanHistoryEntity
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.data.source.network.dto.HistoryScanUpsertDto
import com.fyyadi.scan.domain.model.HistoryScanLocal
import com.fyyadi.scan.domain.model.SaveHistoryScanRequest

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

fun SaveHistoryScanRequest.toHistoryScanUpsertDto(): HistoryScanUpsertDto =
    HistoryScanUpsertDto(
        userEmail = userEmail,
        plantId = plantId,
        accuracy = accuracy
    )

fun HistoryScanLocal.toEntity(): ScanHistoryEntity =
    ScanHistoryEntity(
        userEmail = userEmail,
        plantId = plantId,
        imageResultUri = imageResultUri,
        accuracy = accuracy
    )
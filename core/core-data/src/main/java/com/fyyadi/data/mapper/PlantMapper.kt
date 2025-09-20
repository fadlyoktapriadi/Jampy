package com.fyyadi.data.mapper

import com.fyyadi.data.source.local.room.entity.PlantEntity
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.domain.model.Plant

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

fun Plant.toEntity() = PlantEntity(
    idPlant = idPlant,
    plantName = plantName,
    plantSpecies = plantSpecies,
    plantDescription = plantDescription,
    healthBenefits = healthBenefits,
    processingMethod = processingMethod,
    imagePlant = imagePlant
)

fun PlantEntity.toDomain() = Plant(
    idPlant = idPlant,
    plantName = plantName,
    plantSpecies = plantSpecies,
    plantDescription = plantDescription,
    healthBenefits = healthBenefits,
    processingMethod = processingMethod,
    imagePlant = imagePlant
)
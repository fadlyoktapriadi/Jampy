package com.fyyadi.management.data.mapper

import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.management.data.source.PlantDto
import com.fyyadi.management.domain.model.AddPlant

fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        userId = this.userId ?: 0,
        userFullName = this.userFullName ?: "",
        userEmail = this.userEmail ?: "",
        photoProfile = this.photoProfile,
        role = this.role ?: ""
    )
}

fun List<UserProfileDto>.toUserProfileList(): List<UserProfile> {
    return this.map { it.toUserProfile() }
}

fun AddPlant.toAddPlantDto() = PlantDto(
    plantName = this.plantName,
    plantSpecies = this.plantSpecies,
    plantDescription = this.plantDescription,
    healthBenefits = this.healthBenefits,
    processingMethod = this.processingMethod,
    imagePlant = this.imagePlant
)

fun Plant.toUpdatePlantDto() = PlantDto(
    plantName = this.plantName,
    plantSpecies = this.plantSpecies,
    plantDescription = this.plantDescription,
    healthBenefits = this.healthBenefits,
    processingMethod = this.processingMethod,
    imagePlant = this.imagePlant
)
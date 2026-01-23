package com.fyyadi.management.data.source

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDto(
    @SerialName("plant_name")
    val plantName: String?,
    @SerialName("plant_species")
    val plantSpecies: String?,
    @SerialName("plant_description")
    val plantDescription: String?,
    @SerialName("health_benefits")
    val healthBenefits: String?,
    @SerialName("processing_method")
    val processingMethod: String?,
    @SerialName("image_plant")
    val imagePlant: String?
)
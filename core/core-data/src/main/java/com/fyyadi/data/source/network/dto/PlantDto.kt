package com.fyyadi.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDto(
    @SerialName("plant_id")
    val idPlant: Int? = null,

    @SerialName("plant_name")
    val name: String? = null,

    @SerialName("plant_species")
    val plantSpecies: String? = null,

    @SerialName("plant_description")
    val plantDescription: String? = null,

    @SerialName("health_benefits")
    val healthBenefits: String? = null,

    @SerialName("processing_method")
    val processingMethod: String? = null,

    @SerialName("image_plant")
    val imagePlant: String? = null
)

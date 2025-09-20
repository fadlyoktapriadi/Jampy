package com.fyyadi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Plant(
    val idPlant: Int,
    val plantName: String,
    val plantSpecies: String,
    val plantDescription: String,
    val healthBenefits: String,
    val processingMethod: String,
    val imagePlant: String
)
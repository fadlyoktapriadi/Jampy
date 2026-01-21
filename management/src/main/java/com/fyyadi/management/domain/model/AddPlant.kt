package com.fyyadi.management.domain.model

data class AddPlant(
    val plantName: String,
    val plantSpecies: String,
    val plantDescription: String,
    val healthBenefits: String,
    val processingMethod: String,
    val imagePlant: String
)

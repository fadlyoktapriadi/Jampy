package com.fyyadi.domain.model

data class Plant(
    val idPlant: Int,
    val plantName: String,
    val plantSpecies: String,
    val plantDescription: String,
    val healthBenefits: String,
    val processingMethod: String,
    val imagePlant: Int
)
package com.fyyadi.scan.domain.model

data class PlantLabel(
    val name: String,
    val confidence: Float
)

object PlantLabels {
    val LABELS = listOf(
        "Daun Basil",
        "Daun Belimbing Wuluh",
        "Daun Jambu Biji",
        "Daun Jeruk Nipis",
        "Daun Kelor",
        "Daun Kunyit",
        "Daun Mint",
        "Daun Pepaya",
        "Daun Sirih",
        "Jahe",
        "Kunyit",
        "Lidah Buaya"
    )
}
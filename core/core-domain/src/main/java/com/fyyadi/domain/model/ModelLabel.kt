package com.fyyadi.domain.model

data class PlantLabel(
    val name: String,
    val displayName: String,
    val confidence: Float
)

object PlantLabels {
    val LABELS = listOf(
        "belimbing_wuluh",
        "jahe",
        "jambu_biji",
        "jeruk_nipis",
        "kemangi",
        "kunyit",
        "lidah_buaya",
        "mint",
        "pepaya",
        "sirih"
    )
    
    val DISPLAY_NAMES = mapOf(
        "belimbing_wuluh" to "Belimbing Wuluh",
        "jahe" to "Jahe",
        "jambu_biji" to "Jambu Biji",
        "jeruk_nipis" to "Jeruk Nipis",
        "kemangi" to "Kemangi",
        "kunyit" to "Kunyit",
        "lidah_buaya" to "Lidah Buaya",
        "mint" to "Mint",
        "pepaya" to "Pepaya",
        "sirih" to "Sirih"
    )
}
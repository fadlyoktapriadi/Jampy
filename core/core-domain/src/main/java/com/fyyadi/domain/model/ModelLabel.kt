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
        "belimbing_wuluh" to "Daun Belimbing Wuluh",
        "jahe" to "Jahe",
        "jambu_biji" to "Daun Jambu Biji",
        "jeruk_nipis" to "Daun Jeruk Nipis",
        "kemangi" to "Daun Kemangi",
        "kunyit" to "Kunyit",
        "lidah_buaya" to "Lidah Buaya",
        "mint" to "Daun Mint",
        "pepaya" to "DaunPepaya",
        "sirih" to "Daun Sirih"
    )
}
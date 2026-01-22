package com.fyyadi.scan.domain.model

data class PlantLabel(
    val name: String,
    val displayName: String,
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
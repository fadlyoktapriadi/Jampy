package com.fyyadi.domain.model

data class PlantLabel(
    val name: String,
    val displayName: String,
    val confidence: Float
)

object PlantLabels {
    // Order must match the model's output indices
    val LABELS = listOf(
        "belimbing_wuluh",  // index 0
        "jahe",             // index 1
        "jambu_biji",       // index 2
        "jeruk_nipis",      // index 3
        "kemangi",          // index 4
        "kunyit",           // index 5
        "lidah_buaya",      // index 6
        "mint",             // index 7
        "pepaya",           // index 8
        "sirih"             // index 9
    )

    // Optional: Create a map for reverse lookup
    val LABEL_TO_INDEX = LABELS.mapIndexed { index, label -> label to index }.toMap()
    val INDEX_TO_LABEL = LABELS.mapIndexed { index, label -> index to label }.toMap()

    // Keep your existing DISPLAY_NAMES map
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
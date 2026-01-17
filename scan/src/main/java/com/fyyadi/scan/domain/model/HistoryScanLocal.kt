package com.fyyadi.scan.domain.model

data class HistoryScanLocal(
    val userEmail: String,
    val plantId: Int,
    val imageResultUri: String,
    val accuracy: Int,
)

package com.fyyadi.scan.domain.model

data class SaveHistoryScanRequest(
    val userEmail: String,
    val plantId: Int,
    val accuracy: Int,
)
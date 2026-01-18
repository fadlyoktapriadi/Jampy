package com.fyyadi.scan.domain.model

data class HistoryScan(
    val plantId: Int,
    val plantNamePredict: String,
    val imageResultUri: String,
    val accuracy: Int,
    val date: Long
)

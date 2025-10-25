package com.fyyadi.scan.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryScanUpsertDto(
    @SerialName("user_email") val userEmail: String,
    @SerialName("plant_id") val plantId: Int,
    @SerialName("accuracy") val accuracy: Int
)
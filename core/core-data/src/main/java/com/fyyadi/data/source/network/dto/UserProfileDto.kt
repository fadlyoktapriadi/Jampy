package com.fyyadi.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    @SerialName("user_id")
    val userId: Int? = null,
    @SerialName("user_full_name")
    val userFullName: String?,
    @SerialName("user_email")
    val userEmail: String?,
    @SerialName("photo_profile")
    val photoProfile: String?,
    @SerialName("role")
    val role: String?,
    @SerialName("created_at")
    val createdAt: String? = null
)
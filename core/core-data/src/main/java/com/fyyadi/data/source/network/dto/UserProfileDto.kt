package com.fyyadi.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    @SerialName("id_user")
    val id: Int? = null,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("photo_profile")
    val photoProfile: String?,
    @SerialName("role")
    val role: String?,
    @SerialName("created_at")
    val createdAt: String? = null
)
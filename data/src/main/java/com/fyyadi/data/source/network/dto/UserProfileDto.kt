package com.fyyadi.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val id: Int? = null,
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("role")
    val role: String?,
    @SerialName("created_at")
    val createdAt: String? = null
)
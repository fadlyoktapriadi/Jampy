package com.fyyadi.auth.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddNewUserRequestDto(
    @SerialName("user_full_name")
    val userFullName: String?,
    @SerialName("user_email")
    val userEmail: String?,
    @SerialName("photo_profile")
    val photoProfile: String?,
    @SerialName("role")
    val role: String?
)
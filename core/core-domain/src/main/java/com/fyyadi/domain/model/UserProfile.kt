package com.fyyadi.domain.model

data class UserProfile(
    val userId: Int?,
    val userEmail: String?,
    val userFullName: String?,
    val photoProfile: String?,
    val role: String?
)
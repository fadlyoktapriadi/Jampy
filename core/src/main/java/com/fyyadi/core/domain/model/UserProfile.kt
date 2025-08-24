package com.fyyadi.core.domain.model

data class UserProfile(
    val id: Int?,
    val email: String?,
    val fullName: String?,
    val avatarUrl: String?,
    val role: String?
)
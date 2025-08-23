package com.fyyadi.core.domain.model

data class UserProfile(
    val id: String,
    val email: String?,
    val fullName: String?,
    val avatarUrl: String?,
    val role: String?
)
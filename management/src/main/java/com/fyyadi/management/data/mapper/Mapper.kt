package com.fyyadi.management.data.mapper

import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile

fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        userId = this.userId ?: 0,
        userFullName = this.userFullName ?: "",
        userEmail = this.userEmail ?: "",
        photoProfile = this.photoProfile,
        role = this.role ?: ""
    )
}

fun List<UserProfileDto>.toUserProfileList(): List<UserProfile> {
    return this.map { it.toUserProfile() }
}
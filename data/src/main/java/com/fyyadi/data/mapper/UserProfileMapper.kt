package com.fyyadi.data.mapper

import com.fyyadi.data.source.network.dto.UserProfileDto
import com.fyyadi.domain.model.UserProfile

fun UserProfileDto.toUserProfile(): UserProfile =
    UserProfile(
        id = id,
        email = email,
        fullName = fullName,
        avatarUrl = avatarUrl,
        role = role ?: "user"
    )

fun UserProfile.toUserProfileDto(): UserProfileDto =
    UserProfileDto(
        id = id,
        email = email,
        fullName = fullName,
        avatarUrl = avatarUrl,
        role = role
    )
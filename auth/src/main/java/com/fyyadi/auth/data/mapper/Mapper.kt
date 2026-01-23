package com.fyyadi.auth.data.mapper

import com.fyyadi.auth.data.source.network.AddNewUserRequestDto
import com.fyyadi.domain.model.UserProfile

fun UserProfile.toAddNewUserRequestDto() = AddNewUserRequestDto(
    userFullName = this.userFullName,
    userEmail = this.userEmail,
    photoProfile = this.photoProfile,
    role = this.role
)
package com.fyyadi.auth.domain.usecase

import com.fyyadi.domain.usecase.GetUserProfileUseCase

data class AuthUseCase (
    val signInWithGoogleAuthUseCase: SignInWithGoogleAuthUseCase,
    val checkUserLoginUseCase: CheckUserLoginUseCase,
    val saveSessionLoginUseCase: SaveSessionLoginUseCase,
    val addUserUseCase: AddUserUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getLoginStatusUseCase: GetLoginStatusUseCase,
    val logoutUseCase: LogoutUseCase
)
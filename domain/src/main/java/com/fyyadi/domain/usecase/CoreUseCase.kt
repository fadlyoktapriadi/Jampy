package com.fyyadi.domain.usecase

data class CoreUseCase (
    val authUseCase: AuthUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserLoginUseCase: CheckUserLoginUseCase
)
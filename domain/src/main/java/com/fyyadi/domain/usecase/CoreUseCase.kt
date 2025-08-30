package com.fyyadi.domain.usecase

data class CoreUseCase (
    val authUseCase: AuthUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserLoginUseCase: CheckUserLoginUseCase,
    val clearUserLoginUseCase: ClearUserLoginUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val saveUserLoginUseCase: SaveUserLoginUseCase,
    val getLoginStatusUseCase: GetLoginStatusUseCase,
    val getPlantHomeUseCase: GetPlantHomeUseCase,
    val getDetailPlantUseCase: GetDetailPlantUseCase
)
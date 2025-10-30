package com.fyyadi.domain.usecase

data class CoreUseCase (
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getLoginStatusUseCase: GetLoginStatusUseCase,
    val getPlantHomeUseCase: GetPlantHomeUseCase,
    val getAllPlantUseCase: GetAllPlantsUseCase,
    val getDetailPlantUseCase: GetDetailPlantUseCase,
    val getAllBookmarkedPlantsUseCase: GetAllBookmarkedPlantsUseCase,
    val saveBookmarkPlantUseCase: SaveBookmarkPlantUseCase,
    val removeBookmarkPlantUseCase: RemoveBookmarkPlantUseCase,
    val isPlantBookmarkedUseCase: IsPlantBookmarkedUseCase
)
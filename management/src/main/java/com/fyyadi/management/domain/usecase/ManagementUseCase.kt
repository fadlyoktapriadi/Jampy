package com.fyyadi.management.domain.usecase

data class ManagementUseCase (
    val getAllUsersUseCase: GetAllUsersUseCase,
    val updateUserRoleUseCase: UpdateUserRoleUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val addNewPlantUseCase: AddNewPlantUseCase,
    val updatePlantUseCase: UpdatePlantUseCase,
    val deletePlantUseCase: DeletePlantUseCase
)

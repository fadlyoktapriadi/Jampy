package com.fyyadi.management.data.di

import com.fyyadi.management.data.repository.ManagementRepositoryImpl
import com.fyyadi.management.domain.repository.ManagementRepository
import com.fyyadi.management.domain.usecase.AddNewPlantUseCase
import com.fyyadi.management.domain.usecase.DeletePlantUseCase
import com.fyyadi.management.domain.usecase.DeleteUserUseCase
import com.fyyadi.management.domain.usecase.GetAllUsersUseCase
import com.fyyadi.management.domain.usecase.ManagementUseCase
import com.fyyadi.management.domain.usecase.UpdatePlantUseCase
import com.fyyadi.management.domain.usecase.UpdateUserRoleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagementModule {

    @Provides
    @Singleton
    fun provideManagementRepositoryImpl(
        postgrest: Postgrest
    ): ManagementRepository = ManagementRepositoryImpl(
        postgrest
    )

    @Provides
    @Singleton
    fun providesManagementUseCases(
        managementRepository: ManagementRepository,
    ) = ManagementUseCase(
        getAllUsersUseCase = GetAllUsersUseCase(managementRepository),
        updateUserRoleUseCase = UpdateUserRoleUseCase(managementRepository),
        deleteUserUseCase = DeleteUserUseCase(managementRepository),
        addNewPlantUseCase = AddNewPlantUseCase(managementRepository),
        updatePlantUseCase = UpdatePlantUseCase(managementRepository),
        deletePlantUseCase = DeletePlantUseCase(managementRepository)
    )
}
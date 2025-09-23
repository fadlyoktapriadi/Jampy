package com.fyyadi.jampy.di

import com.fyyadi.data.repository.PlantClassificationRepositoryImpl
import com.fyyadi.domain.repository.PlantClassificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScanModule {

    @Binds
    @Singleton
    abstract fun bindPlantClassificationService(
        plantClassificationServiceImpl: PlantClassificationRepositoryImpl
    ): PlantClassificationRepository
}
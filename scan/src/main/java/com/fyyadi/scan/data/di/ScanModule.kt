package com.fyyadi.scan.data.di

import com.fyyadi.data.source.local.room.dao.ScanHistoryDao
import com.fyyadi.scan.data.repository.PlantClassificationRepositoryImpl
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import com.fyyadi.scan.domain.usecase.DownloadModelUseCase
import com.fyyadi.scan.domain.usecase.GetAllHistoryScanUseCase
import com.fyyadi.scan.domain.usecase.GetDetailResultClassifyUseCase
import com.fyyadi.scan.domain.usecase.PlantClassifyUseCase
import com.fyyadi.scan.domain.usecase.SaveHistoryScanLocalUseCase
import com.fyyadi.scan.domain.usecase.ScanUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScanModule {

    @Provides
    @Singleton
    fun providePlantClassificationRepositoryImpl(
        postgrest: Postgrest,
        dao: ScanHistoryDao
    ): PlantClassificationRepository = PlantClassificationRepositoryImpl(
        postgrest,
        dao
    )


    @Provides
    @Singleton
    fun providesCoreUseCases(
        plantClassificationRepository: PlantClassificationRepository
    ) = ScanUseCase(
        getDetailResultClassifyUseCase = GetDetailResultClassifyUseCase(
            plantClassificationRepository
        ),
        plantClassifyUseCase = PlantClassifyUseCase(
            plantClassificationRepository
        ),
        downloadModelUseCase = DownloadModelUseCase(
            plantClassificationRepository
        ),
        saveHistoryScanLocalUseCase = SaveHistoryScanLocalUseCase(
            plantClassificationRepository
        ),
        getAllHistoryScan = GetAllHistoryScanUseCase(
            plantClassificationRepository
        )
    )
}
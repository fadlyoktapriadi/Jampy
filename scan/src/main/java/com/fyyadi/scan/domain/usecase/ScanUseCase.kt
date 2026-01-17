package com.fyyadi.scan.domain.usecase

data class ScanUseCase (
    val getDetailResultClassifyUseCase: GetDetailResultClassifyUseCase,
    val plantClassifyUseCase: PlantClassifyUseCase,
    val downloadModelUseCase: DownloadModelUseCase,
    val saveHistoryScanUseCase: SaveHistoryScanUseCase,
    val saveHistoryScanLocalUseCase: SaveHistoryScanLocalUseCase
)

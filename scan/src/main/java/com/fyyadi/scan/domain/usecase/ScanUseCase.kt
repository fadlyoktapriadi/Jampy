package com.fyyadi.scan.domain.usecase

data class ScanUseCase (
    val getDetailResultClassifyUseCase: GetDetailResultClassifyUseCase,
    val plantClassifyUseCase: PlantClassifyUseCase,
    val downloadModelUseCase: DownloadModelUseCase,
    val saveHistoryScanLocalUseCase: SaveHistoryScanLocalUseCase,
    val getAllHistoryScan: GetAllHistoryScanUseCase,
)

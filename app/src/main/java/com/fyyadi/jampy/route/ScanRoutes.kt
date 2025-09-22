package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class ScanRoutes : BaseRoute() {

    @Serializable
    data object ScanScreen : ScanRoutes()

    @Serializable
    data object CameraScreen : ScanRoutes()

    @Serializable
    data object ResultScanScreen : ScanRoutes()

}
package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoutes : BaseRoute() {

    @Serializable
    data object HomeScreen : HomeRoutes()

    @Serializable
    data class DetailPlantScreen(val plantId: Int) : HomeRoutes()

    @Serializable
    data object ActivityHistoryScan : HomeRoutes()

    @Serializable
    data class DetailActivityHistoryScanScreen(val plantId: Int, val imageResultUri: String, val accuracy: String) : HomeRoutes()

    @Serializable
    data object BookmarkScreen : HomeRoutes()

    @Serializable
    data object ProfileScreen : HomeRoutes()

}
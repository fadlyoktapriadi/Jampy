package com.fyyadi.jampy.route

import com.fyyadi.domain.model.Plant
import kotlinx.serialization.Serializable

@Serializable
sealed class ManagementRoutes : BaseRoute() {

    @Serializable
    data object PlantManagementScreen : ManagementRoutes()

    @Serializable
    data object UsersManagementScreen : ManagementRoutes()

    @Serializable
    data object AddPlantScreen : ManagementRoutes()
    @Serializable
    data class EditPlantScreen(val plantId: Int) : HomeRoutes()

}
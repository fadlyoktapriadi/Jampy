package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class ManagementRoutes : BaseRoute() {

    @Serializable
    data object PlantManagementScreen : ManagementRoutes()

    @Serializable
    data object UsersManagementScreen : ManagementRoutes()

}
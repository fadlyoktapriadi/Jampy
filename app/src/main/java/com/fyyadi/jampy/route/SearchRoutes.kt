package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class SearchRoutes : BaseRoute() {

    @Serializable
    data object SearchScreen : SearchRoutes()

}
package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class BaseRoute {
    @Serializable
    data object BaseGraph : BaseRoute()
}
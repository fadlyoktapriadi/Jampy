package com.fyyadi.jampy.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoutes: BaseRoute() {

    @Serializable
    data object SplashScreen : AuthRoutes()

    @Serializable
    data object LoginScreen : AuthRoutes()
}
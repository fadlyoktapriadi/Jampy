package com.fyyadi.core.utils

sealed class ResultState {
    data object Success: ResultState()
    data class Error(val message: String): ResultState()
    data class Loading(val isLoading: Boolean): ResultState()
}
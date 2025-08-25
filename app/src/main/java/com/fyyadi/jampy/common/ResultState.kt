package com.fyyadi.jampy.common

sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val message: String?) : ResultState<Nothing>
    object Loading : ResultState<Nothing>
    object Idle : ResultState<Nothing>
}
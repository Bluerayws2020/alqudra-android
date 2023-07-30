package com.blueray.alqudra.model

sealed class NetworkResults<out R> {
    data class Success<out T>(val data: T) : NetworkResults<T>()
    data class NotFound<out T>(val data :T?):NetworkResults<T>()
    data class Error(val exception: Exception) : NetworkResults<Nothing>()
}
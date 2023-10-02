package com.blueray.alqudra.model

sealed class NetworkResults<out R> {
    data class Success<out T>(val data: T) : NetworkResults<T>()
    data class Error(val exception: Exception) : NetworkResults<Nothing>()
}

data class ViewUserProfileModel(
    val `data`: ViewUserProfileData,
    val msg: Msg
)
data class UpdateUserProfile(
    val `data`: List<ViewUserProfileData>,
    val msg: Msg
)

data class Msg(
    val message: String,
    val status: Int
)
data class ViewUserProfileData(
    val Phone: String,
    val dob: String,
    val email: String,
    val name: String
)
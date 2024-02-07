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
    val name: String,
    val img: String
)

data class SendDriverNotificationseModel(
    val `data`: List<NotificationseModel>,
    val msg: Msg
)

data class NotificationseModel(
    val Title: String,
    val body: String,
    val classification: String,
    val order_id: String,
    val time: String,
)
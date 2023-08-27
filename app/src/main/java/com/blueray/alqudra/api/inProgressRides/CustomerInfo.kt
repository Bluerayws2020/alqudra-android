package com.blueray.alqudra.api.inProgressRides

import com.blueray.alqudra.api.inProgressRides.X0
import com.google.gson.annotations.SerializedName

data class CustomerInfo(
    @SerializedName("0") val inneer: X0,
    val img: String
)

data class LoginModel(

    @SerializedName("msg") val status: MessageModel,
    @SerializedName("data") val loginModel: LoginModelRespose,

    )

data class MessageModel(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,


)


data class LoginModelRespose(
    @SerializedName("user_id") val uid: String,
    @SerializedName("email") val email: String,
    @SerializedName("usre_name") val usre_name: String,
    @SerializedName("phone") val phone: String,

    @SerializedName("name") val name: String,


    )
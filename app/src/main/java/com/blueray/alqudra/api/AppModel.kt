package com.blueray.alqudra.api

import com.google.gson.annotations.SerializedName


data class MainMsg(
    @SerializedName("msg") val msg: MsgBody
)

data class MsgBody(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String

)
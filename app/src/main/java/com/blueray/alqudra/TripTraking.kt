package com.blueray.alqudra

import com.google.gson.annotations.SerializedName

data class TripTraking(
    val crunet_step: Int,
    @SerializedName("data") val  items  : List<DetailsData>,
    val msg: Msg,
    val next_step: Int
    val
)
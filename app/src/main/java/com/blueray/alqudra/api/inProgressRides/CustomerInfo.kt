package com.blueray.alqudra.api.inProgressRides

import com.blueray.alqudra.api.inProgressRides.X0
import com.google.gson.annotations.SerializedName

data class CustomerInfo(
    @SerializedName("0") val inneer: X0,
    val img: String
)
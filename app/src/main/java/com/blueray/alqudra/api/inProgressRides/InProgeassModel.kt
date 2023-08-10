package com.blueray.alqudra.api.inProgressRides

data class InProgeassModel(
    val `data`: List<Data>? = null,
    val msg: Msg
)
data class UpdateTripResponse(
    val msg: Msg
)
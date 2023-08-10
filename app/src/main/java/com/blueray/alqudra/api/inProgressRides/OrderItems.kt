package com.blueray.alqudra.api.inProgressRides

data class OrderItems(
    val car: String,
    val customer_request: String,
    val delivery_branch_lat_long : DeliveryBranchLatLong,
    val delivery_branch_name: String,
    val duration_end: String,
    val duration_start: String,
    val receiving_branch_lat_long: ReceivingBranchLatLong,
    val receiving_branch_name: String
)
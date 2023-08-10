package com.blueray.alqudra.api.inProgressRides

import java.io.Serializable

data class Data(
    val currency: String,
    val customer_info: X0 ,
//    val from_branch_group: List<Any>,
//    val from_customer_group: List<Any>,
    val order_id: String,
    val order_items: OrderItems,
    val order_state: String,
    val order_total_price: String,
//    val to_branch_group: List<Any>,
//    val to_customer_group: List<Any>,
    val trip_status: String
) : Serializable

data class LocationInfo(
val name:String,
    val time:String
)
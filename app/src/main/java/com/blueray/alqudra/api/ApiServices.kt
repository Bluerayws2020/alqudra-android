package com.blueray.alqudra.api

import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.LoginModel
import com.blueray.alqudra.api.inProgressRides.Msg
import com.blueray.alqudra.api.inProgressRides.UpdateTripResponse
import com.blueray.alqudra.model.UpdateUserProfile
import com.blueray.alqudra.model.ViewUserProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {

    @Multipart
    @POST("login/")
    suspend fun loginApi(
        @Header("Authorization") auth: String,

        @Part("user_name") phone: RequestBody,
        @Part("password") otp: RequestBody,
        @Part("mac") device_player_id: RequestBody,
        @Part("lang") lang: RequestBody,

        ): LoginModel


    @Multipart
    @POST("inProgressRides")
    suspend fun InPrograssModel(

        @Header("Authorization") auth: String,

        @Part("uid") uid: RequestBody,

        ): InProgeassModel



    @Multipart
    @POST("upcomingrides")
    suspend fun upCommingTrip(

        @Header("Authorization") auth: String,

        @Part("uid") uid: RequestBody,

        ): InProgeassModel


    @Multipart
    @POST("completedRides")
    suspend fun completedRides(

        @Header("Authorization") auth: String,

        @Part("uid") uid: RequestBody,

        ): InProgeassModel


    @Multipart
    @POST("canceledrides")
    suspend fun cancelDrive(

        @Header("Authorization") auth: String,

        @Part("uid") uid: RequestBody,

        ): InProgeassModel
//    updateTripInfo

    @Multipart
    @POST("updateTripInfo")
    suspend fun updateTripInfo(

        @Header("Authorization") auth: String,
        @Part("uid") uid: RequestBody,

        @Part("order_id") order_id: RequestBody,
        @Part("trip_type") trip_type: RequestBody,
        @Part("group_type") group_type: RequestBody,
        @Part("kilos") kilos: RequestBody,
        @Part("fuel") fuel: RequestBody,


        @Part  dashbord :   MultipartBody.Part,
        @Part("lat") lat: RequestBody,
        @Part("lon") long: RequestBody,


        ): UpdateTripResponse

    @Multipart
    @POST("check-order-group-type-status")
    suspend fun orderStatus(

        @Header("Authorization") auth: String,

        @Part("uid") uid: RequestBody,
        @Part("order_id") order_id: RequestBody,

        ): TripTraking

    @Multipart
    @POST("get-profile-by-id")
    suspend fun getProfileById(
        @Header("Authorization") auth: String,
        @Part("uid") uid : RequestBody
    ): ViewUserProfileModel
    @Multipart
    @POST("update-driver-info")
    suspend fun updateDriverInfo(
        @Header("Authorization") auth: String,
        @Part("uid") uid : RequestBody,
        @Part("first_name") first_name : RequestBody,
        @Part("last_name") last_name : RequestBody,
        @Part("dob") dop : RequestBody,
        @Part("phone") phone : RequestBody,
        @Part("email") email : RequestBody
    ): UpdateUserProfile
}

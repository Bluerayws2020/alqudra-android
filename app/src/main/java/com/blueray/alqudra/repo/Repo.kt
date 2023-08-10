package com.blueray.alqudra.repo

import android.os.Message
import android.util.Base64
import android.util.Log
import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.ApiClient
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.Msg
import com.blueray.alqudra.api.inProgressRides.UpdateTripResponse
import com.blueray.alqudra.model.NetworkResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Header
import retrofit2.http.Part
import java.io.File

object Repo {
    // user name and password for OTP basic auth
    const val USER_NAME = "qudrah"
    const val PASSWORD = "PW@1989+-*/"

    suspend fun InProgressRides(
        uid:String,
        lang:String,

    ): NetworkResults<InProgeassModel> {
        return withContext(Dispatchers.IO){

            val langRequestBody=lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody=uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base  ="Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result= ApiClient.retrofitService.InPrograssModel(
                  base,

                    uidBody

                )
                NetworkResults.Success(result)

            }catch (e:Exception){
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun upCommingTrip(
        uid:String,
        lang:String,

        ): NetworkResults<InProgeassModel> {
        return withContext(Dispatchers.IO){

            val langRequestBody=lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody=uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base  ="Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result= ApiClient.retrofitService.upCommingTrip(
                    base,

                    uidBody

                )
                NetworkResults.Success(result)

            }catch (e:Exception){
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun completdTrip(
        uid:String,
        lang:String,

        ): NetworkResults<InProgeassModel> {
        return withContext(Dispatchers.IO) {

            val langRequestBody = lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.completedRides(
                    base,

                    uidBody

                )
                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }



    suspend fun cancelTrip(
        uid:String,
        lang:String,

        ): NetworkResults<InProgeassModel> {
        return withContext(Dispatchers.IO) {

            val langRequestBody = lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.cancelDrive(
                    base,

                    uidBody

                )
                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
    suspend fun tripTracking(
        uid:String,
        lang:String,
        order_id:String,

        ): NetworkResults<TripTraking> {
        return withContext(Dispatchers.IO) {

            val langRequestBody = lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val order_idBody = order_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.orderStatus(
                    base,

                    uidBody,order_idBody

                  )
                Log.d("HHHHH",order_id)

                NetworkResults.Success(result)


            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
    suspend fun updateTtip(
        uid:String,
        lang:String,
        order_id:String,
        trip_type:String,
        group_type:String,
        kilos:String,
        fuel:String,
        img: File,
        lat:String,
        long:String,


        ): NetworkResults<UpdateTripResponse> {
        return withContext(Dispatchers.IO) {

            val langRequestBody = lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val orderBody = order_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val tripTypeBody = trip_type.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val groubTypeBody = group_type.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val kilosBody = kilos.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val fuelBody = fuel.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val latBody = lat.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val longBody = long.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val requestBodyimg = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), img)

            val imgPart = MultipartBody.Part.createFormData("dashbord", img.name, requestBodyimg)



            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.updateTripInfo(
                    base,

                    uidBody,
                    orderBody,
                    tripTypeBody,
                    groubTypeBody,
                    kilosBody,
                    fuelBody,
                    imgPart,latBody,
                    longBody

                )

                Log.d("!E321312",order_id+"-"+group_type+"-"+trip_type+"-"+uid + "-" + kilos+"-"+fuel + "-" + lang + "-" + lat)
                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

}
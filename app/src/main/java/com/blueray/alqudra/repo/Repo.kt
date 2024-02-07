package com.blueray.alqudra.repo

import android.util.Base64
import android.util.Log
import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.ApiClient
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.LoginModel
import com.blueray.alqudra.api.inProgressRides.UpdateTripResponse
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.model.SendDriverNotificationseModel
import com.blueray.alqudra.model.UpdateUserProfile
import com.blueray.alqudra.model.ViewUserProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
                Log.e("***" , result.toString())
                NetworkResults.Success(result)

            }catch (e:Exception){
                Log.e("***" , e.toString())
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
                    latBody,
                    longBody,
                    imgPart

                )

                Log.d("!E321312",order_id+"-"+group_type+"-"+trip_type+"-"+uid + "-" + kilos+"-"+fuel + "-" + lang + "-" + lat)
                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }



    suspend fun loginModel(
        user_name:String,
        password:String,
        mac:String,
        lang:String,



        ): NetworkResults<LoginModel> {
        return withContext(Dispatchers.IO) {

            val langRequestBody = lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val user_nameBody = user_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val macBody = mac.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.loginApi(
                    base,
                    user_nameBody,passwordBody,macBody,langRequestBody



                )

                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
    suspend fun getProfileById(
        uid: String
    ): NetworkResults<ViewUserProfileModel> {
        return withContext(Dispatchers.IO) {

            val uidRequestBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.getProfileById(
                    base,
                    uidRequestBody
                )


                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
    suspend fun updateProfileById(
        uid: String,
        firstName: String,
        lastName: String,
        dob: String,
        phone: String,
        email: String,
        imageData: String?
    ): NetworkResults<UpdateUserProfile> {
        return withContext(Dispatchers.IO) {

            val uidRequestBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val firstNameRequestBody = firstName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val lastNameRequestBody = lastName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val dobRequestBody = dob.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val phoneRequestBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val emailRequestBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val imageFile = File(imageData)
            val commercial_recordBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)
            val commercial_record_part  =  MultipartBody.Part.createFormData("profile_image", firstName, commercial_recordBody)

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.updateDriverInfo(
                    auth = base,
                    uid = uidRequestBody,
                    first_name = firstNameRequestBody,
                    last_name = lastNameRequestBody,
                    dop = dobRequestBody,
                    phone = phoneRequestBody,
                    email = emailRequestBody,
                    image_profile = commercial_record_part)


                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getNotifications(
        uid: String
    ): NetworkResults<SendDriverNotificationseModel> {
        return withContext(Dispatchers.IO) {

            val uidRequestBody = uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val auth = "$USER_NAME:$PASSWORD"
            val base = "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"


            try {
                val result = ApiClient.retrofitService.getSendDriverNotifications(
                    base,
                    uidRequestBody
                )


                NetworkResults.Success(result)

            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }


}

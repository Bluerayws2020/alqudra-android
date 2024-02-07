package com.blueray.alqudra.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.LoginModel
import com.blueray.alqudra.api.inProgressRides.UpdateTripResponse
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.model.SendDriverNotificationseModel
import com.blueray.alqudra.model.UpdateUserProfile
import com.blueray.alqudra.model.ViewUserProfileModel
import com.blueray.alqudra.repo.Repo
import kotlinx.coroutines.launch
import java.io.File

class AppViewModel(application: Application): AndroidViewModel(application) {


    private val deviceId = HelpersUtils.getAndroidID(application.applicationContext)
    private val repo = Repo
    private val language = "ar"
    private val uid = HelpersUtils.getUID(application.applicationContext)


    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(HelpersUtils.SHARED_PREF, AppCompatActivity.MODE_PRIVATE)

    private val inPrograssLive = MutableLiveData<NetworkResults<InProgeassModel>>()
    private val upCommingTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()

    private val completdTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()
    private val cancelTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()


    private val updateTripLive = MutableLiveData<NetworkResults<UpdateTripResponse>>()
    private val tripTrakingLive = MutableLiveData<NetworkResults<TripTraking>>()
    private val loginLiveData = MutableLiveData<NetworkResults<LoginModel>>()
    private val getProfileLiveData =MutableLiveData<NetworkResults<ViewUserProfileModel>>()
    private val getUpdateProfileLiveData =MutableLiveData<NetworkResults<UpdateUserProfile>>()

    private val getNotifications =MutableLiveData<NetworkResults<SendDriverNotificationseModel>>()


    fun retriveInPrograssTrip(
    ){
        viewModelScope.launch {
            inPrograssLive.value=repo.InProgressRides (
               uid,
                language

            )
        }
    }
    fun getInPrograssLive()=inPrograssLive
    fun retriveNotifications(
    ){
        viewModelScope.launch {
            getNotifications.value=repo.getNotifications (
                uid
            )
        }
    }

    fun getNotificationsList()=getNotifications


    fun retriveUpcommingtrip(

    ){
        viewModelScope.launch {
            upCommingTripLive.value=repo.upCommingTrip (
                uid,
                language

            )
        }
    }
    fun getUpcomingTrip()=upCommingTripLive

    fun retriveCompletdTrip(

    ){
        viewModelScope.launch {
            completdTripLive.value=repo.completdTrip (
                uid,
                language

            )
        }
    }
    fun getCompletdTrip()=completdTripLive


    fun retriveCancelTrip(



    ){
        viewModelScope.launch {
            cancelTripLive.value=repo.cancelTrip (
                uid,
                language

            )
        }
    }
    fun getCancelTrip()=cancelTripLive


//    tripTrakingLive

    fun retriveTripTracking(
        orederID:String


    ){
        viewModelScope.launch {
            tripTrakingLive.value=repo.tripTracking (
                uid,
                language,
                orederID

            )
        }
    }
    fun getTripTraking()=tripTrakingLive



    //    updateTripLive
fun updateTrip(

    order_id:String,
    trip_type:String,
    group_type:String,
    kilos:String,
    fuel:String,
    img: File,
    lat:String,
    long:String,

    ){
    viewModelScope.launch {
        updateTripLive.value=repo.updateTtip (
            uid,
            language,
            order_id,
            trip_type,group_type,kilos,fuel,img,lat,long


        )
    }
}
    fun getUpdateTrip()=updateTripLive


    fun retriveLoginModel(
        email:String,
        password:String,



        ){
        viewModelScope.launch {
            loginLiveData.value=repo.loginModel (
              email,password,deviceId,language

            )
        }
    }
    fun getLogin()=loginLiveData


    fun retrieveProfileById(

        ){
        viewModelScope.launch {
            getProfileLiveData.value=repo.getProfileById(uid
            )
        }
    }
    fun getProfileById()=getProfileLiveData

    fun retrieveUpdateProfile(
        firstName: String,
        lastName: String,
        dob: String,
        phone: String,
        email: String,
        imageData: String?
    ){
        viewModelScope.launch {
            getUpdateProfileLiveData.value=repo.updateProfileById(
                uid,
                firstName,
                lastName,
                dob,
                phone,
                email,
                imageData
            )
        }
    }
    fun getUpdateProfile()=getUpdateProfileLiveData



}


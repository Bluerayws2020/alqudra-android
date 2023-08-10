package com.blueray.alqudra.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.Msg
import com.blueray.alqudra.api.inProgressRides.UpdateTripResponse
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.repo.Repo
import kotlinx.coroutines.launch
import java.io.File

class AppViewModel(application: Application): AndroidViewModel(application) {
    private val deviceId = HelpersUtils.getAndroidID(application.applicationContext)
    private val repo = Repo
    private val language = "ar"
    private val uid = "7"


    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(HelpersUtils.SHARED_PREF, AppCompatActivity.MODE_PRIVATE)

    private val inPrograssLive = MutableLiveData<NetworkResults<InProgeassModel>>()
    private val upCommingTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()

    private val completdTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()
    private val cancelTripLive = MutableLiveData<NetworkResults<InProgeassModel>>()


    private val updateTripLive = MutableLiveData<NetworkResults<UpdateTripResponse>>()
    private val tripTrakingLive = MutableLiveData<NetworkResults<TripTraking>>()



    fun retriveInPrograssTrip(



    ){
        viewModelScope.launch {
            inPrograssLive.value=repo.InProgressRides (
               "7",
                language

            )
        }
    }
    fun getInPrograssLive()=inPrograssLive





    fun retriveUpcommingtrip(



    ){
        viewModelScope.launch {
            upCommingTripLive.value=repo.upCommingTrip (
                "7",
                language

            )
        }
    }
    fun getUpcomingTrip()=upCommingTripLive



    fun retriveCompletdTrip(



    ){
        viewModelScope.launch {
            completdTripLive.value=repo.completdTrip (
                "7",
                language

            )
        }
    }
    fun getCompletdTrip()=completdTripLive


    fun retriveCancelTrip(



    ){
        viewModelScope.launch {
            cancelTripLive.value=repo.cancelTrip (
                "7",
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
                "7",
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
            "7",
            language,
            order_id,
            trip_type,group_type,kilos,fuel,img,lat,long


        )
    }
}
    fun getUpdateTrip()=updateTripLive



}


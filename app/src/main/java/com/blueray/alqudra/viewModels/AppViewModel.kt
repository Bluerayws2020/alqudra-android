package com.blueray.alqudra.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.repo.NetworkRepository

class AppViewModel(application: Application): AndroidViewModel(application) {
    private val deviceId = HelpersUtils.getAndroidID(application.applicationContext)
    private val repo = NetworkRepository
    private val language = "ar"


    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(HelpersUtils.SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
}
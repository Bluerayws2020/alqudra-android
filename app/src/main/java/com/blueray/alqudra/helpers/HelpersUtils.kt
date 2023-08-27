package com.blueray.alqudra.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.blueray.alqudra.api.inProgressRides.Data

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.blueray.alqudra.repo.Repo.PASSWORD
import com.blueray.alqudra.repo.Repo.USER_NAME
import java.util.*

object HelpersUtils {

    val SHARED_PREF = "SHARED_PREF"
    var SELECTED_TRIP_TYPE = ""
    var SELECTED_TRIP_STATUS = ""

    var SELECTED_TRIP_TYPE_ID = ""
    var SELECTED_TRIP_STATUS_ID= ""

    fun hideKeyBoard(activity: Activity) {
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        (activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
                as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isNetWorkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return if (networkCapabilities != null) {
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        } else false
    }

    fun showMessage(context: Context,message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("HardwareIds")
    fun getAndroidID(mContext: Context?): String =
        Settings.Secure.getString(mContext?.contentResolver, Settings.Secure.ANDROID_ID)

    fun switchLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)}


    /*this function can be replaced by setting theme to no action bar theme and
      calling AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) in the app class*/
    fun setUpActivity(activity: AppCompatActivity){
        activity.supportActionBar?.hide()
        // calling this method in one Activity made it action in all the activities in the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // should be called Once in App life cycle but no problem to call it more

    }
    const val ARABIC_LANGUAGE = "ar"
    const val ENGLISH_LANGUAGE = "en"

    fun setDefaultLanguage(context: Context, lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
//    fun changeLang(): String {
//        return if (HomeActivity.Lang == ARABIC_LANGUAGE)
//            "en"
//        else
//            "ar"
//    }
    fun getLang(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString("lang", "ar")!!
    }

    fun getUID(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString("uid", "0")!!
    }


    fun saveUserNameAndPassword(mContext: Context?,username:String,password:String){
        val sharedPreferences= mContext?.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        val editor=sharedPreferences?.edit()
        editor?.putString(USER_NAME,username)
        editor?.putString(PASSWORD,password)
        editor?.apply()
    }
    fun getUserNameAndPassword(mContext: Context?,getEmailAndPassword:(email:String?,password:String?)->Unit) {
        val sharedPreferences= mContext?.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        var email=sharedPreferences?.getString(USER_NAME,"")
        var password=sharedPreferences?.getString(PASSWORD,"")
        getEmailAndPassword(email,password)
    }

}
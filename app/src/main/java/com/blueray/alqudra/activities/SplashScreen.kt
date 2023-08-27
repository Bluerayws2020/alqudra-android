package com.blueray.alqudra.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityLoginActivatyBinding
import com.blueray.alqudra.databinding.ActivityMainBinding
import com.blueray.alqudra.databinding.ActivitySplashScreenBinding
import com.blueray.alqudra.helpers.HelpersUtils

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        HelpersUtils.setDefaultLanguage(this,"ar")
        supportActionBar?.hide()




//        check if uid != null

        val shared = getSharedPreferences(HelpersUtils.SHARED_PREF, MODE_PRIVATE)
        val uid = shared.getString("uid", "")

        Log.d("Clint UID ",uid.toString())
        if (!uid.isNullOrEmpty()) {

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }, 3000)


        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivaty::class.java))
                finish()
            }, 3000)

        }
    }
}
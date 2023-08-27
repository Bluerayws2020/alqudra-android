package com.blueray.alqudra.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.blueray.alqudra.R
import com.blueray.alqudra.api.inProgressRides.LoginModel
import com.blueray.alqudra.databinding.ActivityLoginActivatyBinding
import com.blueray.alqudra.databinding.ActivityMainBinding
import com.blueray.alqudra.databinding.FragmentFromBeforeRideBinding
import com.blueray.alqudra.fragments.ProgressDialogFragment
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.getUserNameAndPassword
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel

class LoginActivaty : BaseActivity() {
    private var isChecked=true
    private var progressDialog = ProgressDialogFragment()

    private lateinit var binding: ActivityLoginActivatyBinding
    val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginActivatyBinding.inflate(layoutInflater)
        setContentView(binding.root)
supportActionBar?.hide()

        binding.logins.setOnClickListener {
                viewModel.retriveLoginModel(binding.emilTxt.text.toString(),binding.password.text.toString())

//                HelpersUtils.showMessage(this, "All Feailds Are Requred ")



        }


        getData()


        binding.checkBox.isChecked=isChecked
        if (isChecked){
            getUserNameAndPassword(this){
                    email, password ->
                binding.emilTxt.text= Editable.Factory.getInstance().newEditable(email)
                binding.password.text= Editable.Factory.getInstance().newEditable(password)
            }
        }
        binding.checkBox.setOnClickListener {
            if(isChecked){
                isChecked=false
                binding.emilTxt.text= Editable.Factory.getInstance().newEditable("")
                binding.password.text= Editable.Factory.getInstance().newEditable("")
            }else{
                getUserNameAndPassword(this){
                        email, password ->
                    binding.emilTxt.text= Editable.Factory.getInstance().newEditable(email)
                    binding.password.text= Editable.Factory.getInstance().newEditable(password)
                    isChecked=true

                }
            }

        }
    }


    private fun saveUserData(driver: LoginModel) {
        val sharedPreferences = getSharedPreferences(HelpersUtils.SHARED_PREF, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("uid", driver.loginModel.uid.toString())


            Log.d("UUIDSS",driver.loginModel.uid.toString())
        }.apply()

        val intentSignIn = Intent(this, MainActivity::class.java)
        startActivity(intentSignIn)
        finishAffinity()

    }
    private fun getData() {
        viewModel.getLogin().observe(this) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.status.status == 200) {
                        HelpersUtils.showMessage(this, it.data.status.message.toString())
//                        navController.popBackStack()
                        onBackPressed()

                        saveUserData(it.data)
                    } else {
                        HelpersUtils.showMessage(this, it.data.status.message.toString())

                    }

                }

                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }



}
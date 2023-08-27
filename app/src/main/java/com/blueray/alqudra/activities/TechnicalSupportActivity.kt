package com.blueray.alqudra.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityTechnicalSupportBinding
import com.blueray.alqudra.fragments.CustomDrawerLayout
import com.blueray.alqudra.helpers.HelpersUtils.setUpActivity
import com.blueray.alqudra.helpers.ViewUtils.hide

class TechnicalSupportActivity : BaseActivity() {

    private lateinit var binding : ActivityTechnicalSupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechnicalSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set Up Activity
        setUpActivity(this)


        binding.includedTap.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()



    }
}
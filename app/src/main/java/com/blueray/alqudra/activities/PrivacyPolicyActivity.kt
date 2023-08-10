package com.blueray.alqudra.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityPrivacyPolicyBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.setUpActivity
import com.blueray.alqudra.helpers.ViewUtils.hide

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPrivacyPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // prepareActivity
        setUpActivity(this)

        // show only the back button in the actionBar
        binding.includedTap.notifications.hide()
        binding.includedTap.notifications.hide()

        // on back pressed implementation
        binding.includedTap.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
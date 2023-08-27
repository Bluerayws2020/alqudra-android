package com.blueray.alqudra.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.adapters.VehicleViolationAdapter
import com.blueray.alqudra.databinding.ActivityVehicleViolaitionsBinding
import com.blueray.alqudra.helpers.HelpersUtils.setUpActivity
import com.blueray.alqudra.helpers.ViewUtils.hide

class VehicleViolationsActivity : BaseActivity() {

    private lateinit var vehicleAdapter: VehicleViolationAdapter
    private lateinit var binding:ActivityVehicleViolaitionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleViolaitionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // prepareActivity
        setUpActivity(this)

        // show only the back button in the actionBar
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()

        // on back pressed implementation
        binding.includedTap.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // prepare recycler view
        prepareRecycler()

    }

    // prepare recycler
    private fun prepareRecycler(){
        val lm = LinearLayoutManager(this)
        vehicleAdapter = VehicleViolationAdapter(listOf())
        binding.vehicleViolationsRv.adapter = vehicleAdapter
        binding.vehicleViolationsRv.layoutManager = lm
    }
}
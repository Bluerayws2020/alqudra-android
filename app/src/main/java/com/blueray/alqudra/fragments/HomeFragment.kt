package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.FragmentHomeBinding
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.viewModels.AppViewModel


class HomeFragment: BaseFragment<FragmentHomeBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deliveryTripBtn.setOnClickListener {
            SELECTED_TRIP_TYPE ="Delivery Trips"
            findNavController().navigate(R.id.action_home_to_tripsFragment)
        }
        binding.pickUpTripsBtn.setOnClickListener {
            SELECTED_TRIP_TYPE ="Pickup Trips"
            findNavController().navigate(R.id.action_home_to_tripsFragment)
        }
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
    }


}
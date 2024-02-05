package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.databinding.FragmentHomeBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS_ID
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE_ID
import com.blueray.alqudra.helpers.HelpersUtils.getName
import com.blueray.alqudra.helpers.ViewUtils.hide
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
        val shared = requireContext().getSharedPreferences(HelpersUtils.SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
        var nameA = ""
        // add the name of the user
        HelpersUtils.getName(requireContext() ){
            name ->
            nameA =name
        }
            binding.includeTap.titleName.text = "hi, " + nameA.toString()


        // unlock drawer
        (activity as MainActivity).showDrawer()

        binding.includeTap.menu.setOnClickListener {
            (requireActivity() as MainActivity).openDrawer()
        }

        binding.deliveryTripBtn.setOnClickListener {
            SELECTED_TRIP_TYPE ="Delivery Trips"


            SELECTED_TRIP_TYPE_ID ="1"

            findNavController().navigate(R.id.action_home_to_tripsFragment)
        }
        binding.pickUpTripsBtn.setOnClickListener {
            SELECTED_TRIP_TYPE ="Pickup Trips"

            SELECTED_TRIP_TYPE_ID  ="2"

            findNavController().navigate(R.id.action_home_to_tripsFragment)
        }
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
    }


}
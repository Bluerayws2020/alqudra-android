package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.databinding.FragmentTripsBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS_ID
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.viewModels.AppViewModel

class TripsFragment : BaseFragment<FragmentTripsBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTripsBinding {
        return FragmentTripsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // lock drawer
        (activity as MainActivity).hideMenu()

        HelpersUtils.getName(requireContext()) {
            binding.includeTap.name.text = getString(R.string.hi_zaid_omar, it)
        }

        binding.includeTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressedDispatcher.onBackPressed()
        }
        binding.includeTap.notifications.hide()
        binding.includeTap.menu.hide()
        binding.includeTap.title.text = SELECTED_TRIP_TYPE
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }

        binding.inProcessTripBtn.setOnClickListener {
            SELECTED_TRIP_STATUS = "In Process Trips"
            SELECTED_TRIP_STATUS_ID = "1"
            findNavController().navigate(R.id.action_tripsFragment_to_tripsListFragment)
        }
        binding.upComingBtn.setOnClickListener {
            SELECTED_TRIP_STATUS = "Up Coming Trips"
            SELECTED_TRIP_STATUS_ID = "2"

            findNavController().navigate(R.id.action_tripsFragment_to_tripsListFragment)
        }
        binding.completeTripsBtn.setOnClickListener {
            SELECTED_TRIP_STATUS = "Complete Trips"
            SELECTED_TRIP_STATUS_ID = "3"

            findNavController().navigate(R.id.action_tripsFragment_to_tripsListFragment)
        }
        binding.canceledTripsBtn.setOnClickListener {
            SELECTED_TRIP_STATUS = "Canceled Trips"
            SELECTED_TRIP_STATUS_ID = "4"

            findNavController().navigate(R.id.action_tripsFragment_to_tripsListFragment)
        }
    }
}

package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.RideInfoAdapter
import com.blueray.alqudra.databinding.FragmentTripDataBinding
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.viewModels.AppViewModel


class TripDataFragment : BaseFragment<FragmentTripDataBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var rideInfoAdapter: RideInfoAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTripDataBinding {
        return FragmentTripDataBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includedTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressed()
        }
        binding.includedTap.title.text = SELECTED_TRIP_STATUS
        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }

        rideInfoAdapter = RideInfoAdapter(listOf())
        val lm = LinearLayoutManager(requireContext())
        binding.riderInfoRec.adapter = rideInfoAdapter
        binding.riderInfoRec.layoutManager = lm
        binding.fillFromBeforeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_tripDataFragment_to_fromBeforeRideFragment)
        }


    }

}
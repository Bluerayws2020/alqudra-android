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
import com.blueray.alqudra.databinding.FragmentFromBeforeRideBinding
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.viewModels.FromBeforeRideViewModel


class FromBeforeRideFragment : BaseFragment<FragmentFromBeforeRideBinding,FromBeforeRideViewModel>() {

    override val viewModel by viewModels<FromBeforeRideViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFromBeforeRideBinding {
        return FragmentFromBeforeRideBinding.inflate(layoutInflater)
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
    }
}
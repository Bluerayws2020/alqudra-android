package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.adapters.TravelAdapter
import com.blueray.alqudra.databinding.FragmentTravelBinding
import com.blueray.alqudra.viewModels.TravelViewModel


class TravelFragment : BaseFragment<FragmentTravelBinding,TravelViewModel>() {

    override val viewModel by viewModels<TravelViewModel>()
    private lateinit var travelAdapter: TravelAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTravelBinding {
        return FragmentTravelBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includedTap.titleName.text = "Travel"
        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }

        val lm = LinearLayoutManager(requireContext())
        travelAdapter = TravelAdapter(listOf())
        binding.TravelsRv.adapter = travelAdapter

    }

}
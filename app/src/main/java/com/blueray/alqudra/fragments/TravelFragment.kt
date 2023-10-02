package com.blueray.alqudra.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.TravelAdapter
import com.blueray.alqudra.databinding.FragmentTravelBinding
import com.blueray.alqudra.helpers.HelpersUtils
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

        // unlock drawer
        (activity as MainActivity).showDrawer()
        HelpersUtils.getName(requireContext()) {
            binding.includedTap.titleName.text = getString(R.string.hi_zaid_omar, it)
        }

        HelpersUtils.getName(requireContext()) {
            binding.includedTap.titleName.text = getString(R.string.hi_zaid_omar, it)
        }
        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
        binding.includedTap.menu.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }

//        val lm = LinearLayoutManager(requireContext())
//        travelAdapter = TravelAdapter(listOf())
//        binding.TravelsRv.adapter = travelAdapter

    }

}
package com.blueray.alqudra.fragments

import android.graphics.Canvas.VertexMode
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.TripListAdapter
import com.blueray.alqudra.databinding.FragmentTripsListBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.viewModels.AppViewModel

class TripsListFragment() : BaseFragment<FragmentTripsListBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var tripsAdapter : TripListAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTripsListBinding {
        return FragmentTripsListBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressed()
        }
        binding.includeTap.title.text = HelpersUtils.SELECTED_TRIP_STATUS
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }

        // recycler set up
        val lm = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
        tripsAdapter = TripListAdapter(listOf())
        binding.tripsRec.adapter = tripsAdapter
        binding.tripsRec.layoutManager = lm
        tripsAdapter.onItemClick {
            findNavController().navigate(R.id.action_tripsListFragment_to_tripDataFragment)
        }


    }


}
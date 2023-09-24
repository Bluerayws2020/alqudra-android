package com.blueray.alqudra.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.DetailsData
import com.blueray.alqudra.Group
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.AdabterListItem
import com.blueray.alqudra.adapters.TravelAdapter
import com.blueray.alqudra.adapters.TripListAdapter
import com.blueray.alqudra.api.ItemClickOption
import com.blueray.alqudra.api.OnClickItems
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.databinding.FragmentPickupBinding
import com.blueray.alqudra.databinding.FragmentTripsBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PickupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickupFragment : BaseFragment<FragmentPickupBinding,AppViewModel>(){

    override val viewModel by viewModels<AppViewModel>()

    private lateinit var travelAdapter: AdabterListItem
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPickupBinding {
        return FragmentPickupBinding.inflate(layoutInflater)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orederID = arguments?.getSerializable("orderId") as? String
Log.d("orderIdorderId",orederID.toString())



        viewModel.retriveTripTracking(orederID.toString())
        getData()
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        travelAdapter = AdabterListItem(object : ItemClickOption {
            override fun pressItem(pos: Int) {






            }


        })

        binding.tripsRec.adapter = travelAdapter
        binding.tripsRec.layoutManager = lm

var groubType = ""
        if (travelAdapter.list.size == 0){
            groubType = "1"
        }else if (travelAdapter.list.size == 1){
            groubType = "2"

        }
        else if (travelAdapter.list.size == 2){
            groubType = "3"

        }
        else if (travelAdapter.list.size == 3){
            groubType = "4"

        }
        binding.AddUpdate.setOnClickListener {

            val bundle = Bundle().apply {

                putSerializable("orderId", orederID) // This assumes 'selectedTrip' is Serializable. Change accordingly.
                putSerializable("groubTpe", groubType) // This assumes 'selectedTrip' is Serializable. Change accordingly.

            }

            findNavController().navigate(R.id.action_tripDataFragment_to_fromBeforeRideFragment)



        }






    }


    private fun getData() {
        viewModel.getTripTraking().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        val ls = it.data.items.flatMap { it.groups }
                        travelAdapter.list = ls as ArrayList<Group>
                        travelAdapter.notifyDataSetChanged()

                        HelpersUtils.showMessage(requireContext(), it.data.msg.message.toString())


                    } else {
                        HelpersUtils.showMessage(requireContext(), it.data.msg.message.toString())
                    }
                }
                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }
}
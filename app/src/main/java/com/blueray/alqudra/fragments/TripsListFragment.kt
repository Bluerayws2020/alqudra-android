package com.blueray.alqudra.fragments

import android.graphics.Canvas.VertexMode
import android.os.Bundle
import android.util.Log
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
import com.blueray.alqudra.api.OnClickItems
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.databinding.FragmentTripsListBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE_ID
import com.blueray.alqudra.helpers.HelpersUtils.showMessage
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel

class TripsListFragment() : BaseFragment<FragmentTripsListBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var tripsAdapter: TripListAdapter

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


Log.d("≈!!!", HelpersUtils.SELECTED_TRIP_STATUS_ID)
        if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "1"){
//Inprogercess Trip
            viewModel.retriveInPrograssTrip()
            getData()


        }else  if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "2"){
//            UpComming

            viewModel.retriveUpcommingtrip()
getUpcomming()

        }
        else if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "3"){
            //completd Trrip
            viewModel.retriveCompletdTrip()
getCompletd()
        }
        else if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "4"){
            //cancel Trrip
            viewModel.retriveCancelTrip()
getCancelTrip()

        }

        binding.includeTap.title.text = HelpersUtils.SELECTED_TRIP_STATUS
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
// recycler set up
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        tripsAdapter = TripListAdapter(object :OnClickItems{
            override fun getLisnerItem(pos: Data) {

                val bundle = Bundle()
                bundle.putSerializable("trip_data", pos)
                    // This assumes 'selectedTrip' is Serializable. Change accordingly.


                findNavController().navigate(R.id.action_tripsListFragment_to_tripDataFragment)



            }

        })

        binding.tripsRec.adapter = tripsAdapter
        binding.tripsRec.layoutManager = lm







    }


    private fun getData() {
        viewModel.getInPrograssLive().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        tripsAdapter.list = it.data.data as ArrayList<Data>
                        tripsAdapter.notifyDataSetChanged()
                        showMessage(requireContext(),it.data.msg.message.toString())


                    } else {
showMessage(requireContext(),it.data.msg.message.toString())
                    }
                }
                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }


    private fun getUpcomming() {
        viewModel.getUpcomingTrip().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        tripsAdapter.list = it.data.data as ArrayList<Data>
                        tripsAdapter.notifyDataSetChanged()
                        showMessage(requireContext(),it.data.msg.message.toString())


                    } else {
                        showMessage(requireContext(),it.data.msg.message.toString())
                    }

                }

                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }

    private fun getCompletd() {
        viewModel.getCompletdTrip().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        tripsAdapter.list = it.data.data as ArrayList<Data>
                        tripsAdapter.notifyDataSetChanged()
                        showMessage(requireContext(),it.data.msg.message.toString())


                    } else {
                        showMessage(requireContext(),it.data.msg.message.toString())
                    }

                }

                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }

    private fun getCancelTrip() {
        viewModel.getCancelTrip().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        tripsAdapter.list = it.data.data as ArrayList<Data>
                        tripsAdapter.notifyDataSetChanged()
                        showMessage(requireContext(),it.data.msg.message.toString())


                    } else {
                        showMessage(requireContext(),it.data.msg.message.toString())
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
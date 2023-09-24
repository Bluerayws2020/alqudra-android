package com.blueray.alqudra.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import com.blueray.alqudra.databinding.FragmentPickupBinding
import com.blueray.alqudra.databinding.FragmentTripsBinding
import com.blueray.alqudra.databinding.FragmentTripsListBinding
import com.blueray.alqudra.databinding.PickuplistfragmentBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel

class PickupListFragment :BaseFragment<PickuplistfragmentBinding,AppViewModel>(){

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var tripsAdapter: TripListAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PickuplistfragmentBinding {
        return PickuplistfragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // lock drawer
        (activity as MainActivity).hideMenu()

        binding.includeTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressedDispatcher.onBackPressed()
        }
        binding.includeTap.notifications.hide()
        binding.includeTap.menu.hide()



            viewModel.retriveInPrograssTrip()


        binding.includeTap.title.text = HelpersUtils.SELECTED_TRIP_STATUS
        binding.includeTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
// recycler set up
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        tripsAdapter = TripListAdapter(object : OnClickItems {
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
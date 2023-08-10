package com.blueray.alqudra.fragments

import android.os.Bundle
import android.util.Log
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
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.LocationInfo
import com.blueray.alqudra.databinding.FragmentTripDataBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.helpers.ViewUtils.show
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel


class TripDataFragment : BaseFragment<FragmentTripDataBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var rideInfoAdapter: RideInfoAdapter

   lateinit var  tripData: ArrayList<Data>
private lateinit var  inmodel:InProgeassModel
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


//        val tripData = arguments?.getSerializable("trip_data") as? Data // Change 'Data' to your data class.

        viewModel.retriveInPrograssTrip()
getData()
//        binding.tripId.text =  "#" + tripData?.order_id


//Customer Info
//        binding.customerName.text =   tripData?.customer_info?.first()?.inneer?.name
//        binding.customerPhoneNumber.text =   tripData?.customer_info?.first()?.inneer?.phone_number


//        binding.carName.text =   tripData?.order_items?.car




if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "1"){
    binding.buttonsLayouts.show()

}else {
    binding.buttonsLayouts.hide()

}



        binding.fillFromBeforeBtn.setOnClickListener {
            val bundle = Bundle().apply {

                putSerializable("orderId", tripData.first().order_id) // This assumes 'selectedTrip' is Serializable. Change accordingly.
            }
Log.d("312312",tripData.first().order_id)
            findNavController().navigate(R.id.pickupFragment,bundle)



        }


    }


    private fun getData() {
        viewModel.getInPrograssLive().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {

//                        HelpersUtils.showMessage(requireContext(), it.data.msg.message.toString())

                        tripData = it.data.data as ArrayList<Data>

                        val data = tripData.first()

                        binding.tripId.text = "#" + data.order_id.toString()
                        binding.lastBranchName.text = data.order_items.delivery_branch_name.toString()
                        binding.LastTime.text = data.order_items.duration_end .toString()
                        binding.carName.text = data.customer_info.name.toString()
                        binding.customerPhoneNumber .text = data.customer_info.phone_number.toString()
                        binding.carModel.text = data.order_items.car.toString()
                        var infList  = ArrayList<LocationInfo>()
                        infList?.add(LocationInfo(tripData.first()?.order_items?.delivery_branch_name.toString(),
                            tripData.first()?.order_items?.duration_start.toString()
                        ))
                        rideInfoAdapter = RideInfoAdapter(infList!! )
                        val lm = LinearLayoutManager(requireContext())
                        binding.riderInfoRec.adapter = rideInfoAdapter
                        binding.riderInfoRec.layoutManager = lm



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
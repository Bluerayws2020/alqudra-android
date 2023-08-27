package com.blueray.alqudra.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.RideInfoAdapter
import com.blueray.alqudra.api.OpenGoogleMap
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.api.inProgressRides.InProgeassModel
import com.blueray.alqudra.api.inProgressRides.LocationInfo
import com.blueray.alqudra.databinding.FragmentTripDataBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.helpers.ViewUtils.show
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel


class TripDataFragment : BaseFragment<FragmentTripDataBinding,AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private lateinit var rideInfoAdapter: RideInfoAdapter
    lateinit var navController: NavController

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
        // lock drawer
        (activity as MainActivity).hideMenu()

        binding.includedTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressedDispatcher.onBackPressed()
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()

        binding.includedTap.title.text = SELECTED_TRIP_STATUS
        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }

        navController = findNavController()

//        val tripData = arguments?.getSerializable("trip_data") as? Data // Change 'Data' to your data class.

        viewModel.retriveInPrograssTrip()
getData()

binding.LastRideInfo.setOnClickListener{
val lat  = tripData.first().order_items.receiving_branch_lat_long.lat
    val lon = tripData.first().order_items.receiving_branch_lat_long.lon


    val name = tripData.first().order_items.receiving_branch_name

    val strUri = "http://maps.google.com/maps?q=loc:$lat,$lon $name"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")

    startActivity(intent)
}



if (HelpersUtils.SELECTED_TRIP_STATUS_ID == "1"){
    binding.buttonsLayouts.show()

}else {
    binding.buttonsLayouts.hide()

}



        binding.fillFromBeforeBtn.setOnClickListener {
val intent = Intent(context,FromBeforeRideActivity::class.java)
            intent.putExtra("orederId",tripData.first().order_id)
            startActivity(intent)
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
                        binding.carName.text = data.customer_info?.name.toString()
                        binding.customerPhoneNumber .text = data.customer_info?.phone_number.toString()
                        binding.carModel.text = data.order_items.car.toString()
                        var infList  = ArrayList<LocationInfo>()
                        infList?.add(LocationInfo(tripData.first()?.order_items?.delivery_branch_name.toString(),
                            tripData.first()?.order_items?.duration_start.toString()
                        ))


                        rideInfoAdapter = RideInfoAdapter(infList!! ,object :OpenGoogleMap{

                            override fun onEvent(pos: Int) {

                                val lat  = data.order_items.delivery_branch_lat_long.lat
                                val lon = data.order_items.delivery_branch_lat_long.lon
                                val name = data.order_items.delivery_branch_name
                                val strUri = "http://maps.google.com/maps?q=loc:$lat,$lon $name"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")

                                startActivity(intent)

                            }

                        })
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
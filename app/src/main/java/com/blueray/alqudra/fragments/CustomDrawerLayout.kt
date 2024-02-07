package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.FragmentCustomDrawerLayoutBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel
import com.bumptech.glide.Glide


class CustomDrawerLayout : Fragment() {

    private lateinit var binding : FragmentCustomDrawerLayoutBinding
    val viewModel: AppViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.retrieveProfileById()
        getUserData()

        binding = FragmentCustomDrawerLayoutBinding.inflate(layoutInflater)

        // set click listeners
        binding.profileLayout.setOnClickListener {
            onItemSelected?.invoke(Options.PROFILE_LAYOUT)
            setSelectedItem(Options.PROFILE_LAYOUT)
        }
        binding.drivingViolationLayout.setOnClickListener {
            onItemSelected?.invoke(Options.DRIVING_VIOLATION)
            setSelectedItem(Options.DRIVING_VIOLATION)
        }
        binding.notificationLayout.setOnClickListener {
            onItemSelected?.invoke(Options.NOTIFICATIONS)
            setSelectedItem(Options.NOTIFICATIONS)
        }
        binding.termsAndConditionsLayout.setOnClickListener {
            onItemSelected?.invoke(Options.TERMS_AND_CONDITIONS)
            setSelectedItem(Options.TERMS_AND_CONDITIONS)
        }
        binding.callSupport.setOnClickListener {
            onItemSelected?.invoke(Options.CALL_SUPPORT)
            setSelectedItem(Options.CALL_SUPPORT)
        }
        binding.SignOut.setOnClickListener {
            onItemSelected?.invoke(Options.SIGN_OUT)
            setSelectedItem(Options.SIGN_OUT)
        }
        binding.headerLayout.setOnClickListener {
            onItemSelected?.invoke(Options.MY_DATA)
            setSelectedItem(Options.MY_DATA)
        }


        return binding.root
    }
    companion object{

        // implement onItemClickListener
        private var onItemSelected:((itemId:Options)->Unit)? = null
        fun onItemSelectedListener(listener:((itemId:Options)->Unit)){
            onItemSelected = listener
        }

        // selectedItem for layout changes
        private var _selectedItem:Options = Options.PROFILE_LAYOUT
        fun setSelectedItem(selectedItem:Options){
            when(selectedItem){
                 Options.PROFILE_LAYOUT->{

                }
                else ->{

                }
            }
            _selectedItem = selectedItem
        }

        // todo add function to add data to the drawer header
    }

    enum class Options {
        PROFILE_LAYOUT,
        DRIVING_VIOLATION,
        NOTIFICATIONS,
        TERMS_AND_CONDITIONS,
        CALL_SUPPORT,
        SIGN_OUT,
        MY_DATA
    }

    private fun getUserData(){

        viewModel.getProfileById().observe(this){
                result->
            when(result){
                is NetworkResults.Success -> {
                    if (result.data.msg.status == 200){
                        val data = result.data.data
                        binding.nameTv.text =data.name
                        binding.mobileNumberTv.setText(data.Phone)
                        Glide.with(requireContext()).load(data.img).placeholder(R.drawable.profile_dummy_img).into(binding.profileImage)

                    }else{
                        HelpersUtils.showMessage(
                            requireContext(),
                            result.data.msg.message ?: getString(R.string.error)
                        )
                    }
                }
                is NetworkResults.Error ->{
                    result.exception.printStackTrace()
                    HelpersUtils.showMessage(requireContext(), getString(R.string.error))
                }
            }
        }
    }
}

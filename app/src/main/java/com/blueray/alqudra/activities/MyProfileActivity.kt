package com.blueray.alqudra.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityMyProfileBinding
import com.blueray.alqudra.fragments.BaseFragment
import com.blueray.alqudra.helpers.HelpersUtils.getName
import com.blueray.alqudra.helpers.HelpersUtils.setName
import com.blueray.alqudra.helpers.HelpersUtils.showMessage
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.helpers.ViewUtils.isInputEmpty
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel
import java.util.*

class MyProfileActivity : BaseFragment<ActivityMyProfileBinding,AppViewModel>() {

    override val viewModel: AppViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ActivityMyProfileBinding {
        val binding : ActivityMyProfileBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        return binding
    }

    private var dateOfBirth = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add the name of the user
        getName(requireContext()) {
            binding.includedTap.titleName.text = getString(R.string.hi_zaid_omar, it)
        }

        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
        binding.includedTap.menu.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        // call API
        viewModel.retrieveProfileById()

        // call observers
        getUpdatedData()
        getUserData()


        // on save click
        binding.SaveBtn.setOnClickListener {
            validateData()
        }

        // on datePicker click
        binding.birthDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val formattedDate = String.format(
                        Locale.ENGLISH,
                        "%1\$d-%2\$d-%3\$d",
                        dayOfMonth,
                        (monthOfYear + 1),
                        year
                    )
                    binding.birthDatePicker.setText(formattedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }



    }

    private fun validateData(){
        if (binding.fullNameEt.isInputEmpty()){
            showMessage(requireContext(), getString(R.string.fullNameError))
            binding.fullNameEt.requestFocus()
            binding.fullNameEt.error = getString(R.string.fullNameError)
            return
        }

        if (binding.mobileNumberET.isInputEmpty()){
            showMessage(requireContext(), getString(R.string.mobile_number_error))
            binding.mobileNumberET.requestFocus()
            binding.mobileNumberET.error = getString(R.string.mobile_number_error)
            return
        }

        if (binding.emailEt.isInputEmpty()){
            showMessage(requireContext(), getString(R.string.email_error))
            binding.emailEt.requestFocus()
            binding.emailEt.error = getString(R.string.email_error)
            return
        }

        // Split the input string by space
        val parts = binding.fullNameEt.text.toString().trim().split(" ")

        // Extract the first name (everything except the last word)
        val firstName = parts.dropLast(1).joinToString(" ")

        // Extract the last name (the last word)
        val lastName = parts.last()
        val date = binding.birthDatePicker.text.toString()
        val phone = binding.mobileNumberET.text.toString()
        val email = binding.emailEt.text.toString()

        // call API
        viewModel.retrieveUpdateProfile(firstName, lastName,date,phone,email)


    }

    // observe to live data
    private fun getUserData(){

        viewModel.getProfileById().observe(this){
            result->
            when(result){
                is NetworkResults.Success -> {
                    if (result.data.msg.status == 200){
                        val data = result.data.data
                        binding.userNameTv.text =data.name
                        binding.fullNameEt.setText(data.name)
                        binding.emailEt.setText(data.email)
                        binding.mobileNumberET.setText(data.Phone)
                        binding.birthDatePicker.setText(data.dob)

                        // todo id to be discussed
                        binding.iDTextView.hide()

                    }else{
                        showMessage(requireContext(),result.data.msg.message ?: getString(R.string.error))
                    }
                }
                is NetworkResults.Error ->{
                    result.exception.printStackTrace()
                    showMessage(requireContext(), getString(R.string.error))
                }
            }
        }
    }
    // observe to live data
    private fun getUpdatedData(){

        viewModel.getUpdateProfile().observe(this){
            result->
            when(result){
                is NetworkResults.Success -> {
                    if (result.data.msg.status == 200){
                        showMessage(requireContext(),result.data.msg.message)
                        val data = result.data.data[0]
                        binding.userNameTv.text =data.name
                        binding.fullNameEt.setText(data.name)
                        binding.emailEt.setText(data.email)
                        binding.mobileNumberET.setText(data.Phone)
                        binding.birthDatePicker.setText(data.dob)
                        binding.includedTap.titleName.text =getString(R.string.hi_zaid_omar,data.name)
                        setName(requireContext(),data.name)
                        // todo id to be discussed
                        binding.iDTextView.hide()


                    }else{
                        showMessage(requireContext(),result.data.msg.message ?: getString(R.string.error))
                    }
                }
                is NetworkResults.Error ->{
                    result.exception.printStackTrace()
                    showMessage(requireContext(), getString(R.string.error))
                }
            }
        }
    }
}
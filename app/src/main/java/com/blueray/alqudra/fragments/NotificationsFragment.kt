package com.blueray.alqudra.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.NotificationAdapter
import com.blueray.alqudra.databinding.FragmentNotificationsBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel


class NotificationsFragment : BaseFragment<FragmentNotificationsBinding,AppViewModel>() {


    override val viewModel by viewModels<AppViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding {
        return FragmentNotificationsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // lock drawer
        (activity as MainActivity).hideMenu()
        HelpersUtils.getName(requireContext()) {
            binding.includedTap.name.text = getString(R.string.hi_zaid_omar, it)
        }
        binding.includedTap.title.hide()

        binding.includedTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressedDispatcher.onBackPressed()
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()

        //initRv()

        viewModel.retriveNotifications()
        getData()


    }

    private fun getData() {
        viewModel.getNotificationsList().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {


                        val list = it.data.data
                        HelpersUtils.showMessage(requireContext(), it.data.msg.message.toString())
//                        if (it.data.data?.isEmpty()){
//                            binding.notificationsRv.hide()
//
//                        }else {
//                            binding.notificationsRv.show()
//
//                        }

                        Log.e("***", it.toString())
                        binding.notificationsRv.layoutManager = LinearLayoutManager(requireContext())
                        binding.notificationsRv.adapter = NotificationAdapter(list)

                    } else {
                        HelpersUtils.showMessage(requireContext(), it.data.msg.message.toString())
                    }
                }
                is NetworkResults.Error -> {
                    hideProgress()
                    Log.e("***", it.exception.toString())
                }

                else -> {}
            }
        }
    }


}
package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.adapters.NotificationAdapter
import com.blueray.alqudra.databinding.FragmentNotificationsBinding
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.viewModels.NotificationViewModel


class NotificationsFragment : BaseFragment<FragmentNotificationsBinding,NotificationViewModel>() {

    override val viewModel by viewModels<NotificationViewModel>()
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

        binding.includedTap.back.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressedDispatcher.onBackPressed()
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()

        initRv()


    }

    private fun initRv() {
        // init recycler
        binding.notificationsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationsRv.adapter = NotificationAdapter(listOf())
    }

}
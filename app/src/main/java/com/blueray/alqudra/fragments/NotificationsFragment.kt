package com.blueray.alqudra.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.FragmentNotificationsBinding
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

    }

}
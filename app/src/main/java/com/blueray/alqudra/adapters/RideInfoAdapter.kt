package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.databinding.RideInfoItemBinding
// todo change the model of list
class RideInfoAdapter(val list:List<String>):RecyclerView.Adapter<RideInfoAdapter.MyViewHolder>() {
    inner class MyViewHolder (var binding : RideInfoItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RideInfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int =2
//        list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        TODO implementation of this
    }
}
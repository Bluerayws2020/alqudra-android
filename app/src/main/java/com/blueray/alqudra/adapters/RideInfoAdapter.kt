package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.api.OpenGoogleMap
import com.blueray.alqudra.api.inProgressRides.LocationInfo
import com.blueray.alqudra.databinding.RideInfoItemBinding
class RideInfoAdapter(val list:List<LocationInfo>,val oo: OpenGoogleMap):RecyclerView.Adapter<RideInfoAdapter.MyViewHolder>() {
    inner class MyViewHolder (var binding : RideInfoItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RideInfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.branchName.text = list[position].name
        holder.binding.time.text = list[position].time

        holder.itemView.setOnClickListener {
            oo.onEvent(0)
        }



    }
}
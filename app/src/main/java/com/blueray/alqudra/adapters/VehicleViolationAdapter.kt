package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.databinding.VehicleViolationsItemBinding

class VehicleViolationAdapter(
    // todo change list Model
    var list : List<String>
):RecyclerView.Adapter<VehicleViolationAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : VehicleViolationsItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = VehicleViolationsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 10
    //list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        TODO("Not yet implemented")
    }
}
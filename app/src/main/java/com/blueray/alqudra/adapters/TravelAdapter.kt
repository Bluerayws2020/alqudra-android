package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.databinding.TravelItemBinding

class TravelAdapter(
    // todo change list model
val list:List<String>
): RecyclerView.Adapter<TravelAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding : TravelItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TravelAdapter.MyViewHolder {
        val binding = TravelItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 5
    // list.size

    override fun onBindViewHolder(holder: TravelAdapter.MyViewHolder, position: Int) {
//        TODO implementation of this
    }
}
package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.DetailsData
import com.blueray.alqudra.TripTraking
import com.blueray.alqudra.api.ItemClickOption
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.databinding.TravelItemBinding

class TravelAdapter(
    val clikc: ItemClickOption,

): RecyclerView.Adapter<TravelAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding : TravelItemBinding):ViewHolder(binding.root)
    var list= ArrayList<DetailsData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TravelAdapter.MyViewHolder {
        val binding = TravelItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() =list.size
    // list.size

    override fun onBindViewHolder(holder: TravelAdapter.MyViewHolder, position: Int) {
//        TODO implementation of this
        holder.binding.date.text = list[position].order_id
    }
}
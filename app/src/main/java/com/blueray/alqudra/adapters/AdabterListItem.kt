package com.blueray.alqudra.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.DetailsData
import com.blueray.alqudra.Group
import com.blueray.alqudra.api.ItemClickOption
import com.blueray.alqudra.databinding.TravelItemBinding

class AdabterListItem(
    // todo change list model
    val lisnter: ItemClickOption
): RecyclerView.Adapter<AdabterListItem.MyViewHolder>(){
    inner class MyViewHolder(val binding : TravelItemBinding):ViewHolder(binding.root)
    var list= ArrayList<Group>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdabterListItem.MyViewHolder {
        val binding = TravelItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size
    // list.size

    override fun onBindViewHolder(holder: AdabterListItem.MyViewHolder, position: Int) {
//        TODO implementation of this
        val data = list[position]
holder.binding.tripStatus.text = "Full:${data.fuel} | Status:${data.type} | Killos:${data.kilos} "

        holder.itemView.setOnClickListener{
            lisnter.pressItem(position)
        }
    }
}
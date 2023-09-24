package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.alqudra.databinding.InnerNotificationItemBinding

class InnerNotificationAdapter (
// todo change list model
var list: List<String>
):RecyclerView.Adapter<InnerNotificationAdapter.MyViewHolder>() {
    inner class  MyViewHolder(val binding : InnerNotificationItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = InnerNotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 2
    //list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){

    }

}
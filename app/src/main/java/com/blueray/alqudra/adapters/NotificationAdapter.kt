package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.databinding.NotificationItemBinding

class NotificationAdapter(
    // todo change list model
    var list: List<String>
):RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {
    inner class  MyViewHolder(val binding : NotificationItemBinding ):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 5
    //list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
//        val data = list[position]
        val innerAdapter = InnerNotificationAdapter(listOf())
        holder.apply {
            binding.NotificationsRv.layoutManager = LinearLayoutManager(holder.itemView.context)
            binding.NotificationsRv.adapter = innerAdapter
        }
    }

}
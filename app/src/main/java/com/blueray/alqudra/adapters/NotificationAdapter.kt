package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.api.OnItemClickListener
import com.blueray.alqudra.databinding.InnerNotificationItemBinding
import com.blueray.alqudra.model.NotificationseModel

class NotificationAdapter(
    // todo change list model
    var list: List<NotificationseModel>,
    private val clickListener: OnItemClickListener
):RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {
    inner class  MyViewHolder(val binding : InnerNotificationItemBinding ):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding = InnerNotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= list.size
    //list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){

        val item = holder.itemView
        item.setOnClickListener { clickListener.onItemClick(position) }

//        val data = list[position]
        holder.binding.notificationTitel.text = list[position].Title
        holder.binding.time.text = list[position].time
        holder.binding.from.text = list[position].from
        holder.binding.To.text = list[position].to
    }

}
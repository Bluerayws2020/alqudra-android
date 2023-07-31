package com.blueray.alqudra.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.databinding.TripListItemBinding

class TripListAdapter(
    // todo change the list Type
    val list: List<String>
    ):RecyclerView.Adapter<TripListAdapter.TripListViewHolder>() {
    inner class TripListViewHolder(val binding: TripListItemBinding ):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val binding = TripListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TripListViewHolder(binding)
    }

    override fun getItemCount() =10
//    =list.size

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        // todo implement here
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
    }
    private var onItemClickListener:((position:Int)->Unit)? = null
    fun onItemClick(listener : ((position:Int)->Unit)){
        onItemClickListener = listener
    }

}
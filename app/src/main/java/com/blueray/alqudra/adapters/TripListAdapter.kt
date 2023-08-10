package com.blueray.alqudra.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blueray.alqudra.api.OnClickItems
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.databinding.TripListItemBinding

class TripListAdapter(
    // todo change the list Type
    val lisner: OnClickItems
    ):RecyclerView.Adapter<TripListAdapter.TripListViewHolder>() {
    inner class TripListViewHolder(val binding: TripListItemBinding ):ViewHolder(binding.root)
    var list= ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val binding = TripListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TripListViewHolder(binding)
    }

    override fun getItemCount() =list.size
//    =list.size

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        // todo implement here
        holder.itemView.setOnClickListener {
            Log.d("TripListAdapter", "Item at position $position clicked")
//            onItemClickListener?.invoke(0)
            lisner.getLisnerItem(list[position])
        }

        holder.binding.tripId.text = "#" + list[position].order_id.toString()

    }


}
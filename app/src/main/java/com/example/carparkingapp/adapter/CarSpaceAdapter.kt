package com.example.carparkingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carparkingapp.databinding.ItemSpace1Binding
import com.example.carparkingapp.models.Space

class CarSpaceAdapter(var list: ArrayList<Space>, var listener: CarSpaceAdapter.OnClickListener) :
    RecyclerView.Adapter<CarSpaceAdapter.Vh>() {

    inner class Vh(var itemUserBinding: ItemSpace1Binding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(space: Space) {
            itemUserBinding.apply {
                itemView.isEnabled = false
                if (space.isEmpty) {
                    itemView.isEnabled = true
                    image.visibility = View.GONE
                    stateText.visibility = View.VISIBLE
                } else {
                    itemView.isEnabled = false
                    image.visibility = View.VISIBLE
                    stateText.visibility = View.GONE
                }
            }
            itemView.setOnClickListener {
                listener.clickSpaceListener(space, itemUserBinding, position = list.indexOf(space))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSpace1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface OnClickListener {
        fun clickSpaceListener(space: Space, itemSpace1Binding: ItemSpace1Binding, position: Int)
    }
}
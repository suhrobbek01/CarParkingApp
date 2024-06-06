package com.example.carparkingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carparkingapp.databinding.FragmentPaymentBinding
import com.example.carparkingapp.databinding.PaymentItemBinding
import com.example.carparkingapp.models.Payment

class PaymentAdapter(var list: ArrayList<Payment>, var listener: PaymentAdapter.OnCLickListener) :
    RecyclerView.Adapter<PaymentAdapter.Vh>() {
    inner class Vh(var itemUserBinding: PaymentItemBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(payment: Payment) {
            itemUserBinding.apply {
                imageCard.setImageResource(payment.image)
                nameCard.text = payment.name
            }
            itemView.setOnClickListener {
                listener.onClick(itemUserBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(PaymentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface OnCLickListener {
        fun onClick(paymentBinding: PaymentItemBinding)
    }

}
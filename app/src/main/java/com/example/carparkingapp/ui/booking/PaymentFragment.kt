package com.example.carparkingapp.ui.booking

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.carparkingapp.R
import com.example.carparkingapp.adapter.PaymentAdapter
import com.example.carparkingapp.databinding.FragmentPaymentBinding
import com.example.carparkingapp.databinding.PaymentItemBinding
import com.example.carparkingapp.models.Payment

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var adapter: PaymentAdapter
    private var isCardSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPayment)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""
        val titleTextView =
            binding.toolbarPayment.findViewById<TextView>(R.id.payment_toolbar_title)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.back)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(drawable)
        titleTextView.setTextColor(Color.WHITE)
        binding.toolbarPayment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        loadList()

        binding.selectBtn.setOnClickListener {
            if (isCardSelected) {
                findNavController().navigate(R.id.action_paymentFragment_to_successFragment)
            } else {
                Toast.makeText(requireContext(), "Please, select card", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun loadList() {
        var list = ArrayList<Payment>()
        list.add(Payment("VISA", R.drawable.visa))
        list.add(Payment("MASTERCARD", R.drawable.mastercard))
        adapter = PaymentAdapter(list, object : PaymentAdapter.OnCLickListener {
            override fun onClick(paymentBinding: PaymentItemBinding) {
                paymentBinding.root.setBackgroundResource(R.drawable.space_item_back_selected)
                isCardSelected = true
            }
        })
        binding.rv.adapter = adapter
    }
}
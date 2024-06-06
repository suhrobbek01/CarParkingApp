package com.example.carparkingapp.ui.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carparkingapp.R
import com.example.carparkingapp.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {
    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        binding.goMain.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }

}
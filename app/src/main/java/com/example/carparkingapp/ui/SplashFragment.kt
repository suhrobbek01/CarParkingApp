package com.example.carparkingapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carparkingapp.MainActivity
import com.example.carparkingapp.R
import com.example.carparkingapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val value = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_appInfoFragment)
                }
            }
        }
        value.start()
        return binding.root
    }
}
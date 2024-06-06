package com.example.carparkingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.booking_nav_host_fragment).navigateUp()
    }
}
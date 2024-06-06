package com.example.carparkingapp.ui.booking

import android.annotation.SuppressLint
import android.app.TimePickerDialog
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
import com.example.carparkingapp.databinding.FragmentParkingInfoBinding
import com.example.carparkingapp.models.Space
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class ParkingInfoFragment : Fragment() {
    private lateinit var binding: FragmentParkingInfoBinding
    private var startTimeValue: String = ""
    private var endTimeValue: String = ""
    private var startTimeValue2: String = ""
    private var endTimeValue2: String = ""
    private lateinit var spacesRef: DatabaseReference
    private lateinit var spaceList: ArrayList<Space>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentParkingInfoBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarParkingInfo)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""
        val titleTextView =
            binding.toolbarParkingInfo.findViewById<TextView>(R.id.park_info_toolbar_title)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.back)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(drawable)
        titleTextView.setTextColor(Color.WHITE)
        binding.toolbarParkingInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        spaceList = ArrayList()
        spacesRef = FirebaseDatabase.getInstance().reference.child("spaces")
        val position = arguments?.getInt("position")?.plus(1)
        val cal = Calendar.getInstance()
        val startTimeT = Calendar.getInstance()
        val endTimeT = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        binding.apply {
            starttimePicker.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    requireContext(), { view, hourOfDay, minute ->
                        startTimeT.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        startTimeT.set(Calendar.MINUTE, minute)

                        startTimeValue = String.format("%02d:%02d", hourOfDay, minute)
                        startTime.text = startTimeValue
                    }, hour, minute, true
                )
                timePickerDialog.show()
            }
            endtimePicker.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    requireContext(), { view, hourOfDay, minute ->
                        endTimeT.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        endTimeT.set(Calendar.MINUTE, minute)

                        endTimeValue = String.format("%02d:%02d", hourOfDay, minute)
                        endTime.text = endTimeValue
                    }, hour, minute, true
                )
                timePickerDialog.show()
            }
            totalText.setOnClickListener {
                val diffMillis = endTimeT.timeInMillis - startTimeT.timeInMillis
                val hours = diffMillis / (1000 * 60 * 60)    // Convert milliseconds to hours
                val minutes = (diffMillis / (1000 * 60)) % 60
                totalAmount.text = "${hours * 2}.00 $/$hours hours"
            }
            goBtn.setOnClickListener {
                if ((startTime.text != "Select start time" && endTime.text != "Select end time")) {
                    val hashMap = HashMap<String, Any>()
                    hashMap["startTime"] = startTimeValue
                    hashMap["endTime"] = endTimeValue
                    spacesRef.child("space$position")
                        .updateChildren(hashMap)
                    findNavController().navigate(R.id.action_parkingInfoFragment_to_paymentFragment)
                } else {
                    Toast.makeText(requireContext(), "Please, select time", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return binding.root
    }
}
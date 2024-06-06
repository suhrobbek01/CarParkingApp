package com.example.carparkingapp.ui.booking

import android.graphics.Color
import android.os.Build
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
import com.example.carparkingapp.adapter.CarSpaceAdapter
import com.example.carparkingapp.databinding.FragmentChooseSpaceBinding
import com.example.carparkingapp.databinding.ItemSpace1Binding
import com.example.carparkingapp.models.Space
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.*
import com.google.firebase.database.ValueEventListener

class ChooseSpaceFragment : Fragment() {
    private lateinit var binding: FragmentChooseSpaceBinding
    private lateinit var spaceAdapter: CarSpaceAdapter
    private lateinit var spaceList: ArrayList<Space>
    private lateinit var refSpaces: DatabaseReference
    private var firebaseUser: FirebaseUser? = null
    var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseSpaceBinding.inflate(inflater, container, false)
        refSpaces = getInstance().reference.child("spaces")
        spaceList = ArrayList()
        firebaseUser = FirebaseAuth.getInstance().currentUser

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarChoose)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""
        val titleTextView = binding.toolbarChoose.findViewById<TextView>(R.id.toolbar_title)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.back)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(drawable)

        titleTextView.setTextColor(Color.WHITE)
        binding.toolbarChoose.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.selectBtn.setOnClickListener {
            if ((selectedPosition != -1)) {
                val hashMap = HashMap<String, Any>()
                hashMap["isEmpty"] = false
                hashMap["username"] = firebaseUser?.displayName ?: ""

                refSpaces.child("space${selectedPosition + 1}").updateChildren(hashMap)
                val bundle = Bundle()
                bundle.putInt("position", selectedPosition)
                findNavController().navigate(
                    R.id.action_chooseSpaceFragment_to_parkingInfoFragment,
                    bundle
                )
            } else {
                Toast.makeText(requireContext(), "Please select any place", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        loadSpaces()

        return binding.root
    }

    private fun loadSpaces() {
        refSpaces.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    spaceList.clear()
                    for (child in snapshot.children) {
                        val space = child.getValue(Space::class.java)
                        if (space != null) {
                            spaceList.add(space)
                        }
                    }
                    spaceAdapter =
                        CarSpaceAdapter(spaceList, object : CarSpaceAdapter.OnClickListener {
                            override fun clickSpaceListener(
                                space: Space, itemSpace1Binding: ItemSpace1Binding, position: Int
                            ) {
                                selectedPosition = position
                                itemSpace1Binding.root.setBackgroundResource(R.drawable.space_item_back_selected)
                            }
                        })
                    binding.spaceRv.adapter = spaceAdapter
                } else {
                    Toast.makeText(requireContext(), "Snapshot not exists", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
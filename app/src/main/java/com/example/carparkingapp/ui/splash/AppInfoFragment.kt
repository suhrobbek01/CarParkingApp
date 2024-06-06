package com.example.carparkingapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.carparkingapp.R
import com.example.carparkingapp.adapter.ViewPagerAdapter
import com.example.carparkingapp.databinding.FragmentAppInfoFragmenrBinding

class AppInfoFragment : Fragment() {
    private lateinit var binding: FragmentAppInfoFragmenrBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInfoFragmenrBinding.inflate(inflater, container, false)
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.apply {
            viewPager.adapter = viewPagerAdapter
            dotsIndicator.attachTo(viewPager)

            skipBtn.setOnClickListener {
                viewPager.currentItem = 2
                continueBtn.text = getString(R.string.go)
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            continueBtn.text = "Continue"
                            infoText.text = "Efficient Parking Management"
                        }

                        1 -> {
                            continueBtn.text = "Continue"
                            infoText.text = "Seamless Payment Integration"
                        }

                        2 -> {
                            continueBtn.text = "Go"
                            infoText.text = "Enhanced Security and Monitoring"
                        }
                    }
                }

                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
            })
            continueBtn.setOnClickListener {
                val currentItem = viewPager.currentItem
                when (currentItem) {
                    0 -> {
                        viewPager.setCurrentItem(1)
                        infoText.text="Seamless Payment Integration"
                    }

                    1 -> {
                        viewPager.setCurrentItem(2)
                        continueBtn.setText("Go")
                        infoText.text="Enhanced Security and Monitoring"
                    }

                    2 -> {
                        findNavController().navigate(R.id.action_appInfoFragment_to_authFragment)
                    }
                }
            }
        }
        return binding.root
    }
}
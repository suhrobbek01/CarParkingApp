package com.example.carparkingapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carparkingapp.ui.splash.Fragment1
import com.example.carparkingapp.ui.splash.Fragment2
import com.example.carparkingapp.ui.splash.Fragment3

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            Fragment1()
        } else if (position == 1) {
            Fragment2()
        } else {
            Fragment3()
        }
    }
}
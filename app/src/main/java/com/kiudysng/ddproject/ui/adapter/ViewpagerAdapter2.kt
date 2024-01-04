package com.kiudysng.ddproject.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerAdapter2 ( private val fragmentActivity: FragmentManager,
private val mFragments: ArrayList<Fragment>,private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentActivity,lifecycle) {
    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}
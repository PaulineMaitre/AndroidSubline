package com.example.subline.find

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class FindTabLayoutAdapter (fm : FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList = arrayListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(frag : Fragment){
        fragmentList.add(frag)
    }
}

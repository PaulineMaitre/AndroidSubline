package com.example.subline.find

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class FindTabLayoutAdapter (fm : FragmentManager): FragmentPagerAdapter(fm) {


    val fragment_list = arrayListOf<Fragment>()
    val fragment_title = arrayListOf<String>()



    override fun getItem(position: Int): Fragment {
        return fragment_list[position]
    }

    override fun getCount(): Int {
        return fragment_list.size
    }

    fun addFragment(frag : Fragment){
        fragment_list.add(frag)
    }
}

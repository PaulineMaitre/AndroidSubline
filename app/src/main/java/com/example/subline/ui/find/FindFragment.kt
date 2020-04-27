package com.example.subline.ui.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.subline.R
import com.example.subline.ui.find.bus.FindBus
import com.example.subline.ui.find.metros.FindMetros
import com.example.subline.ui.find.nocti.FindNocti
import com.example.subline.ui.find.rer.FindRER
import com.example.subline.ui.find.tram.FindTram
import com.google.android.material.tabs.TabLayout

class FindFragment : Fragment() {

    private lateinit var viewpager : ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var myfragment : View


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        myfragment = inflater.inflate(R.layout.fragment_find, container, false)

        viewpager = myfragment.findViewById(R.id.fragment_rechercheinterne)
        setupViewPager(viewpager);
        tabLayout = myfragment.findViewById(R.id.tablayout_find)
        tabLayout.setupWithViewPager(viewpager)
        setupTabIcons()

        return myfragment
    }

    fun setupViewPager(viewPager : ViewPager){
        var adapter : FindTabLayoutAdapter = FindTabLayoutAdapter(childFragmentManager)
        adapter.addFragment(FindMetros())
        adapter.addFragment(FindRER())
        adapter.addFragment(FindTram())
        adapter.addFragment(FindBus())
        adapter.addFragment(FindNocti())
        viewPager.adapter = adapter

    }

    fun setupTabIcons(){
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.logo_metro)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.logo_rer)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.logo_tram)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.logo_bus)
        tabLayout.getTabAt(4)!!.setIcon(R.drawable.logo_noctilien)
    }
}

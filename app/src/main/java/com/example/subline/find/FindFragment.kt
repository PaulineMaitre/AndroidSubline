package com.example.subline.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.subline.R
import com.example.subline.find.findTransport.FindBus
import com.example.subline.find.findTransport.FindMetros
import com.example.subline.find.findTransport.FindNoctilien
import com.example.subline.find.findTransport.FindRER
import com.example.subline.find.findTransport.FindStation
import com.example.subline.find.findTransport.FindTram
import com.google.android.material.tabs.TabLayout

class FindFragment : Fragment() {

    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var myFragment : View


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        myFragment = inflater.inflate(R.layout.fragment_find, container, false)

        viewPager = myFragment.findViewById(R.id.searchViewPager)
        setupViewPager(viewPager);
        tabLayout = myFragment.findViewById(R.id.tablayout_find)
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()

        return myFragment
    }

    private fun setupViewPager(viewPager : ViewPager){
        var adapter = FindTabLayoutAdapter(childFragmentManager)
        adapter.addFragment(FindStation())
        adapter.addFragment(FindMetros())
        adapter.addFragment(FindRER())
        adapter.addFragment(FindTram())
        adapter.addFragment(FindBus())
        adapter.addFragment(FindNoctilien())
        viewPager.adapter = adapter

    }

    private fun setupTabIcons(){
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.logo_location_blue)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.logo_metro)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.logo_rer)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.logo_tram)
        tabLayout.getTabAt(4)!!.setIcon(R.drawable.logo_bus)
        tabLayout.getTabAt(5)!!.setIcon(R.drawable.logo_noctilien)
    }
}

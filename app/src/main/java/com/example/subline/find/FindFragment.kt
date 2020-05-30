package com.example.subline.find

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.subline.R
import com.example.subline.find.buses.FindBus
import com.example.subline.find.metros.FindMetros
import com.example.subline.find.nocti.FindNoctilien
import com.example.subline.find.rers.FindRER
import com.example.subline.find.tram.FindTram
import com.example.subline.home.QRCode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class FindFragment : Fragment() {

    private lateinit var viewpager : ViewPager
    private lateinit var tabLayout: TabLayout



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_find, container, false)
        val fabQRCode : FloatingActionButton = view.findViewById(R.id.fabQRcode)
        viewpager = view.findViewById(R.id.fragment_rechercheinterne)
        setupViewPager(viewpager);
        tabLayout = view.findViewById(R.id.tablayout_find)
        tabLayout.setupWithViewPager(viewpager)
        setupTabIcons()

        fabQRCode.setOnClickListener { view ->
            val intent = Intent(this.context, QRCode::class.java)
            startActivity(intent)
            true
        }

        return view
    }

    private fun setupViewPager(viewPager : ViewPager){
        var adapter : FindTabLayoutAdapter = FindTabLayoutAdapter(childFragmentManager)
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

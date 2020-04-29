package com.example.subline.find.metros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R

/**
 * A simple [Fragment] subclass.
 */
class FindMetros : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_metros, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.find_all_metro_rv)
        val rv2 = view.findViewById<RecyclerView>(R.id.find_all_stations_rv)

        var metros = listOf<String>("1","2","3","3b","4","5","6","7","7b","8","9","10","11","12","13","14","Orv","Fun")

        rv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rv2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rv.adapter = AllMetrosAdapter(metros,rv2)
        return view
    }

}


package com.example.subline.ui.find

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.subline.R
import com.example.subline.ui.find.metros.AllMetrosAdapter
import kotlinx.android.synthetic.main.fragment_find_metros.*

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
        var list = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14)
        rv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rv.adapter = AllMetrosAdapter(list)
        return view
    }

}

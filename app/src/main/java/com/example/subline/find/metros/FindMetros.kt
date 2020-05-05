package com.example.subline.find.metros

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindMetros: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_metros, container, false)
        val allMetrosRv = view.findViewById<RecyclerView>(R.id.allMetrosRv)
        val allStationsRv = view.findViewById<RecyclerView>(R.id.allStationsRv)

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var metros: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_METRO)
            results.result.metros.map {
                metros.add(it.code)
            }
        }

        allMetrosRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        allStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allMetrosRv.adapter = AllMetrosAdapter(metros, allStationsRv)
        return view
    }

}


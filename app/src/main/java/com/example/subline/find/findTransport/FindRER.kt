package com.example.subline.find.findTransport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.findResults.AllLinesAdapter
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.PICTO_RER
import com.example.subline.utils.TYPE_RER
import com.example.subline.utils.retrofit
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindRER : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_metro_rer_tram_nocti, container, false)
        val allLinesRv = view.findViewById<RecyclerView>(R.id.allLinesRv)
        val allStationsRv = view.findViewById<RecyclerView>(R.id.allStationsRv)
        val listStationsTextView = view.findViewById<TextView>(R.id.listStationsTextView)

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var rers: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_RER)
            results.result.rers.map {
                if(!rers.contains(it.code)) {
                    rers.add(it.code)
                }
            }
        }

        allLinesRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        allStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allLinesRv.adapter = AllLinesAdapter(rers, allStationsRv, listStationsTextView, TYPE_RER, PICTO_RER)
        return view
    }

}

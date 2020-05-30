package com.example.subline.find.tram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.subline.R
import com.example.subline.find.AllLinesAdapter
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.PICTO_TRAM
import com.example.subline.utils.TYPE_TRAM
import com.example.subline.utils.retrofit
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindTram : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_tram, container, false)
        val allTramsRv = view.findViewById<RecyclerView>(R.id.allTramsRv)
        val allTramStationsRv = view.findViewById<RecyclerView>(R.id.allTramStationsRv)
        val listStationsTextView = view.findViewById<TextView>(R.id.listStationsTextView)

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var tramways: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_TRAM)
            results.result.tramways.map {
                if(!tramways.contains(it.code)) {
                    tramways.add(it.code)
                }
            }
            val t11 = tramways[0]
            tramways.removeAt(0)
            tramways.add(t11)
        }

        allTramsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        allTramStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allTramsRv.adapter = AllLinesAdapter(
            tramways,
            allTramStationsRv,
            listStationsTextView,
            TYPE_TRAM,
            PICTO_TRAM
        )
        return view
    }

}

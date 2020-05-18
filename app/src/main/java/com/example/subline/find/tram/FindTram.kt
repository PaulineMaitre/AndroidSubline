package com.example.subline.find.tram

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

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var trams: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_TRAM)
            results.result.tramways.map {
                if(!trams.contains(it.code)) {
                    trams.add(it.code)
                }
            }
            Log.d("EPF", "${trams}")
            val t11 = trams.get(0)
            trams.removeAt(0)
            trams.add(t11)
            Log.d("EPF", "${trams}")
        }

        allTramsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        allTramStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allTramsRv.adapter = AllTramsAdapter(trams, allTramStationsRv)
        return view
    }

}

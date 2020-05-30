package com.example.subline.find.buses

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.example.subline.R
import com.example.subline.find.AllStationsAdapter
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_BUS
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.fragment_find_bus.*
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindBus : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_bus, container, false)
        val searchBus = view.findViewById<AutoCompleteTextView>(R.id.searchBus)
        val allStationsRv = view.findViewById<RecyclerView>(R.id.allBusStationsRv)

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var buses: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_BUS)
            results.result.buses.map {
                if(!buses.contains(it.code)) {
                    buses.add(it.code)
                }
            }
            Log.d("EPF", "bus: ${buses.size} - ${buses}")
        }
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, buses)
        searchBus.threshold = 0
        searchBus.setAdapter(adapter)
        searchBus.setOnItemClickListener { parent, viewSearch, position, id ->
            hideKeyboard(view.context, view)
            val bus = parent.getItemAtPosition(position).toString()
            val listStations = getListOfStations(view, bus)
            var pictoInt = resources.getIdentifier("b$bus", "drawable", "com.example.subline")
            if(pictoInt == 0) pictoInt = R.drawable.logo_bus
            allStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            allStationsRv.adapter = AllStationsAdapter(
                listStations,
                pictoInt,
                bus,
                TYPE_BUS
            )
        }
        return view
    }

    private fun getListOfStations(view: View, bus: String): List<String> {
        var listStations = arrayListOf<String>()
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        try {
            runBlocking {
                val results = service.getStations(TYPE_BUS, bus)
                results.result.stations.map {
                    listStations.add(it.name)
                    listStations.sort()
                }
                Log.d("EPF", "statBus $listStations")
            }
            listStationsTextView.isVisible = true
        } catch (e: retrofit2.HttpException) {
            listStationsTextView.isVisible = false
            Toast.makeText(view.context, R.string.lineError, Toast.LENGTH_LONG).show()
        }
        return listStations
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

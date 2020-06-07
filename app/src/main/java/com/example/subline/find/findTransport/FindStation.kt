package com.example.subline.find.findTransport

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.AllLines
import com.example.subline.data.DatabaseJSON
import com.example.subline.utils.*
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FindStation(val stationName: String = ""): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_station, container, false)

        val f = File(context?.filesDir,"TransportLines.json")
        if (f.length() == 0L) {
            DatabaseJSON().save(this.context,"TransportLines.json", requestAllLines())
        }
        val lines = DatabaseJSON().load(this.context,"TransportLines.json", AllLines.Lines::class.java)

        extractListStations(view, lines)

        return view
    }

    private fun dropDownListStations(view: View, listStationsDisplay: ArrayList<String>, listStationsSort: ArrayList<AllLines.Station>, lines: AllLines.Lines) {
        val searchStation = view.findViewById<AutoCompleteTextView>(R.id.searchStation)
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, listStationsDisplay)
        searchStation.threshold = 0
        searchStation.setAdapter(adapter)
        searchStation.setOnItemClickListener { parent, _, position, _ ->
            hideKeyboard(view.context, view)
            selectStation(parent.getItemAtPosition(position).toString(), view, lines, listStationsSort)
        }
    }

    private fun selectStation(stationName: String, view: View, lines: AllLines.Lines, listStationsSort: ArrayList<AllLines.Station>) {

        val listLinesTV = view.findViewById<TextView>(R.id.listLinesTextView)

        val slugStation = getSlugFromStationName(listStationsSort, stationName)
        val linesByStation: ArrayList<AllLines.Line> = getLinesByStation(view.context, listLinesTV, lines, slugStation)
        val listPicto = getListPicto(view.context, linesByStation)
        val allLinesInStationRv = view.findViewById<RecyclerView>(R.id.allLinesInStationRv)
        setAdapterStation(activity, allLinesInStationRv, stationName, linesByStation, listPicto)
    }

    private fun extractListStations(view: View, lines: AllLines.Lines) {
        var listStations = arrayListOf<AllLines.Station>()
        lines.allLines.map {
            it.listStations.map { stations ->
                listStations.add(stations)
            }
        }
        val listStationsSort: ArrayList<AllLines.Station> = deleteDuplicateStations(listStations)

        val listStationsDisplay: ArrayList<String> = ArrayList()
        listStationsSort.map {
            listStationsDisplay.add(it.name)
        }

        dropDownListStations(view, listStationsDisplay, listStationsSort, lines)
    }

    private fun deleteDuplicateStations(listStations: ArrayList<AllLines.Station>): ArrayList<AllLines.Station> {
        // delete stations that are twice in listStations
        val listStationsSort: ArrayList<AllLines.Station> = ArrayList()
        for (elementStationsLines in listStations) {
            var findElement = false
            for (elementStationsLinesSort in listStationsSort) {
                if (elementStationsLinesSort.slug == elementStationsLines.slug) {
                    findElement = true
                }
            }
            if (!findElement) {
                listStationsSort.add(elementStationsLines)
            }
        }
        return listStationsSort
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getSlugFromStationName(listStationsSort: ArrayList<AllLines.Station>, stationName: String): String {
        var slugStation = ""
        listStationsSort.map {
            if (stationName == it.name) {
                slugStation = it.slug
            }
        }
        return slugStation
    }
}


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

    /*private fun setAdapter(view: View, stationName: String, linesByStation: ArrayList<AllLines.Line>, listPicto: List<Int>) {
        val allLinesInStationRv = view.findViewById<RecyclerView>(R.id.allLinesInStationRv)
        allLinesInStationRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allLinesInStationRv.adapter = AllLinesByStationAdapter(stationName, linesByStation, listPicto)
    }*/

    /*private fun getListPicto(lines: ArrayList<AllLines.Line>): List<Int> {
        var listPicto: ArrayList<Int> = ArrayList()

        lines.map {
            val type = it.transportType[0]
            val code = it.lineCode.toLowerCase()
            var pictoInt = resources.getIdentifier("$type$code", "drawable", "com.example.subline")

            if(pictoInt == 0) {
                when(it.transportType) {
                    TYPE_METRO -> pictoInt = R.drawable.logo_bus
                    TYPE_RER -> pictoInt = R.drawable.logo_rer
                    TYPE_TRAM -> pictoInt = R.drawable.logo_tram
                    TYPE_BUS -> pictoInt = R.drawable.logo_bus
                    TYPE_NOCTI -> pictoInt = R.drawable.logo_noctilien
                }
            }
            listPicto.add(pictoInt)
        }
        return listPicto
    }*/

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

    /*private fun requestAllLines(): AllLines.Lines {
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var lines = AllLines.Lines()
        runBlocking {
            val results = service.getAllLines()
            results.result.metros.map {lines.addLine(AllLines.Line(it.code, it.name, it.directions, getListOfStations(service, TYPE_METRO, it.code), TYPE_METRO))}
            results.result.rers.map {lines.addLine(AllLines.Line(it.code, it.name, it.directions, getListOfStations(service, TYPE_RER, it.code), TYPE_RER))}
            results.result.tramways.map {lines.addLine(AllLines.Line(it.code, it.name, it.directions, getListOfStations(service, TYPE_TRAM, it.code), TYPE_TRAM))}
            results.result.buses.map {lines.addLine(AllLines.Line(it.code, it.name, it.directions, getListOfStations(service, TYPE_BUS, it.code), TYPE_BUS))}
            results.result.noctiliens.map {lines.addLine(AllLines.Line(it.code, it.name, it.directions, getListOfStations(service, TYPE_NOCTI, it.code), TYPE_NOCTI))}
        }
        return lines
    }*/


    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /*private fun getListOfStations(service: RatpService, type: String, code: String): MutableList<AllLines.Station> {
        var listStations = arrayListOf<AllLines.Station>()
        try {
            runBlocking {
                val results = service.getStations(type, code)
                results.result.stations.map {
                    val station = AllLines.Station(it.name, it.slug)
                    listStations.add(station)
                }
            }
        } catch(e: retrofit2.HttpException) {
            Log.e("Subline", "Bad request $e")
        }
        return listStations
    }*/

    private fun getSlugFromStationName(listStationsSort: ArrayList<AllLines.Station>, stationName: String): String {
        var slugStation = ""
        listStationsSort.map {
            if (stationName == it.name) {
                slugStation = it.slug
            }
        }
        return slugStation
    }

    /*private fun getLinesByStation(view: View, lines: AllLines.Lines, slugStation: String): ArrayList<AllLines.Line> {
        val linesByStation: ArrayList<AllLines.Line> = ArrayList()

        lines.allLines.map { line ->
            line.listStations.map {
                if(it.slug == slugStation) {
                    if(!linesByStation.contains(line))
                    {
                        linesByStation.add(line)
                        Log.d("EPF", "match line ${line}")//.transportType} - ${line.lineCode}")
                    }
                }
            }
        }
        if(linesByStation.size > 0) listLinesTextView.isVisible = true
        else {
            listLinesTextView.isVisible = false
            Toast.makeText(view.context, R.string.lineError, Toast.LENGTH_LONG).show()
        }
        return linesByStation
    }*/
}


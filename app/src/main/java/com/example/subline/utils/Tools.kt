package com.example.subline.utils

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.AllLines
import com.example.subline.find.findResults.AllLinesByStationAdapter
import com.example.subline.service.RatpService
import kotlinx.coroutines.runBlocking

fun requestAllLines(): AllLines.Lines {
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
}

private fun getListOfStations(service: RatpService, type: String, code: String): MutableList<AllLines.Station> {
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
}

fun getLinesByStation(context: Context, listLinesTV: TextView, lines: AllLines.Lines, slugStation: String): ArrayList<AllLines.Line> {

    val linesByStation: ArrayList<AllLines.Line> = ArrayList()

    lines.allLines.map { line ->
        line.listStations.map {
            if(it.slug == slugStation) {
                if(!linesByStation.contains(line))
                {
                    linesByStation.add(line)
                }
            }
        }
    }
    if(linesByStation.size > 0) listLinesTV.isVisible = true
    else {
        listLinesTV.isVisible = false
        Toast.makeText(context, R.string.lineError, Toast.LENGTH_LONG).show()
    }
    return linesByStation
}

fun getListPicto(context: Context, lines: ArrayList<AllLines.Line>): List<Int> {
    var listPicto: ArrayList<Int> = ArrayList()

    lines.map {
        val type = it.transportType[0]
        val code = it.lineCode.toLowerCase()
        var pictoInt = context.resources.getIdentifier("$type$code", "drawable", "com.example.subline")
        if(pictoInt == 0) pictoInt = R.drawable.logo_ratp
        listPicto.add(pictoInt)
    }
    return listPicto
}

fun getNameFromSlug(slug: String, lines: AllLines.Lines): String {
    var stationName = ""
    lines.allLines.map {line ->
        line.listStations.map {
            if (slug == it.slug) {
                stationName = it.name
            }
        }
    }
    return stationName
}

fun setAdapterStation(activity: FragmentActivity?, allLinesInStationRv: RecyclerView, stationName: String, linesByStation: ArrayList<AllLines.Line>, listPicto: List<Int>) {
    allLinesInStationRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
    allLinesInStationRv.adapter = AllLinesByStationAdapter(stationName, linesByStation, listPicto)
}

fun setAdapterQRCodeResult(context: Context, allLinesInStationRv: RecyclerView, stationName: String, linesByStation: ArrayList<AllLines.Line>, listPicto: List<Int>) {
    allLinesInStationRv.layoutManager = LinearLayoutManager(context)
    allLinesInStationRv.adapter = AllLinesByStationAdapter(stationName, linesByStation, listPicto)
}
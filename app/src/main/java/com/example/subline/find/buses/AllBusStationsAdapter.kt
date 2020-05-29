package com.example.subline.find.buses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.metros.HoraireMetro
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_BUS
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.activity_horaire.*
import kotlinx.android.synthetic.main.list_station_item.view.*
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

class AllBusStationsAdapter (val stations: List<String>, val pictoline: Int, val bus: String): RecyclerView.Adapter<AllBusStationsAdapter.BusViewHolder>() {

    class BusViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return BusViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, HoraireMetro::class.java)
            val destinations = getDestinations(it.context, bus)
            Log.d("EPF", "dest1 $destinations")
            Log.d("EPF", "size=${destinations.size}")
            if(destinations.size != 0) {
                intent.putStringArrayListExtra("destinations", destinations)
                intent.putExtra("station", stat)
                intent.putExtra("pictoline", pictoline)
                intent.putExtra("line", bus)
                intent.putExtra("transportType", TYPE_BUS)
                it.context.startActivity(intent)
                true
            }
        }

    }

    fun getDestinations(context: Context, bus: String): ArrayList<String> {
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var listDestinations = arrayListOf<String>()
        try {
            runBlocking {
                val results = service.getDestinations(TYPE_BUS, bus)
                val direct1 = results.result.destinations[0].name
                listDestinations.add(direct1)
                if (results.result.destinations.size > 1) {
                    val direct2 = results.result.destinations[1].name
                    listDestinations.add(direct2)
                }
            }
        } catch (e: retrofit2.HttpException) {
            Toast.makeText(context, R.string.scheduleError, Toast.LENGTH_SHORT).show()
            Log.d("EPF", "catched !! ${e.message}")
        }
        return listDestinations
    }

}
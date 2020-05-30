package com.example.subline.find

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.list_station_item.view.*
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

class AllStationsAdapter (val listStations: List<String>, val pictoLine: Int, val lineCode: String, val transportType: String): RecyclerView.Adapter<AllStationsAdapter.StationsViewHolder>() {

    class StationsViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return StationsViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = listStations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        var stat = listStations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, ScheduleActivity::class.java)
            val destinations = getDestinations(it.context, lineCode)
            if(destinations.size != 0) {
                intent.putStringArrayListExtra("destinations", destinations)
                intent.putExtra("station", stat)
                intent.putExtra("pictoline", pictoLine)
                intent.putExtra("line", lineCode)
                intent.putExtra("transportType", transportType)
                it.context.startActivity(intent)
                true
            }
        }
    }

    private fun getDestinations(context: Context, lineCode: String): ArrayList<String> {
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var listDestinations = arrayListOf<String>()
        try {
            runBlocking {
                val results = service.getDestinations(transportType, lineCode)
                val direct1 = results.result.destinations[0].name
                listDestinations.add(direct1)
                if (results.result.destinations.size > 1) {
                    val direct2 = results.result.destinations[1].name
                    listDestinations.add(direct2)
                }
            }
        } catch (e: retrofit2.HttpException) {
            Toast.makeText(context, R.string.scheduleError, Toast.LENGTH_LONG).show()
        }
        return listDestinations
    }

}
package com.example.subline.find.findResults

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.AllLines
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import kotlinx.android.synthetic.main.list_lines_in_station_item.view.*
import kotlinx.coroutines.runBlocking

class AllLinesByStationAdapter (private val stationName: String, private val linesByStation: ArrayList<AllLines.Line>, private val listPicto: List<Int>): RecyclerView.Adapter<AllLinesByStationAdapter.LinesViewHolder>(){

    class LinesViewHolder(val lineView: View) : RecyclerView.ViewHolder(lineView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_lines_in_station_item, parent,false)

        return LinesViewHolder(view)
    }

    override fun getItemCount(): Int = linesByStation.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LinesViewHolder, position: Int) {
        var line = linesByStation[position]
        val listDirections = line.directions.split("/")
        holder.lineView.directionWayA.text = listDirections[0]
        holder.lineView.directionWayR.text = listDirections[1]
        holder.lineView.stationTypeIcon.setImageResource(
            when(line.transportType) {
                TYPE_METRO -> R.drawable.logo_metro
                TYPE_RER -> R.drawable.logo_rer
                TYPE_TRAM -> R.drawable.logo_tram
                TYPE_BUS -> R.drawable.logo_bus
                TYPE_NOCTI -> R.drawable.logo_nocti
                else -> R.drawable.logo_ratp
            }
        )
        holder.lineView.stationLineIcon.setImageResource(listPicto[position])

        holder.lineView.setOnClickListener {
            val intent= Intent(it.context, ScheduleActivity::class.java)
            val destinations = getDestinations(it.context, line)
            if(destinations.size != 0) {
                intent.putStringArrayListExtra("destinations", destinations)
                intent.putExtra("station", stationName)
                intent.putExtra("pictoline", listPicto[position])
                intent.putExtra("line", line.lineCode)
                intent.putExtra("transportType", line.transportType)
                it.context.startActivity(intent)
                true
            }
        }
    }

    private fun getDestinations(context: Context, line: AllLines.Line): ArrayList<String> {
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var listDestinations = arrayListOf<String>()
        try {
            runBlocking {
                val results = service.getDestinations(line.transportType, line.lineCode)
                val direct1 = results.result.destinations[0].name
                listDestinations.add(direct1)
                if (results.result.destinations.size > 1) {
                    val direct2 = results.result.destinations[1].name
                    listDestinations.add(direct2)
                }
            }
        } catch (e: retrofit2.HttpException) {
            Toast.makeText(context, R.string.lineError, Toast.LENGTH_LONG).show()
        }
        return listDestinations
    }

}
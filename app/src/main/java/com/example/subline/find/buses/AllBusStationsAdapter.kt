package com.example.subline.find.buses

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_station_item.view.*

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
            val intent= Intent(it.context, HoraireBus::class.java)
            intent.putExtra("station", stat)
            intent.putExtra("pictoline", pictoline)
            intent.putExtra("line", bus)
            it.context.startActivity(intent)
            true
        }

    }

}
package com.example.subline.find.tram

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_station_item.view.*

class AllTramStationsAdapter (val stations: List<String>, val pictoline: Int, val tram: String): RecyclerView.Adapter<AllTramStationsAdapter.TramsViewHolder>() {

    class TramsViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramsViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return TramsViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TramsViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, HoraireTram::class.java)
            intent.putExtra("station", stat)
            intent.putExtra("pictoline", pictoline)
            intent.putExtra("line", tram)
            it.context.startActivity(intent)
            true
        }

    }

}
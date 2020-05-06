package com.example.subline.find.rers

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_station_item.view.*

class AllRerStationsAdapter (val stations: List<String>, val pictoline: Int, val rer: String): RecyclerView.Adapter<AllRerStationsAdapter.RersViewHolder>() {

    class RersViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RersViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return RersViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RersViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, HoraireRer::class.java)
            intent.putExtra("station", stat)
            intent.putExtra("pictoline", pictoline)
            intent.putExtra("line", rer)
            //if(rer != "C" && rer != "D") {
                it.context.startActivity(intent)
            //}
            true
        }

    }

}
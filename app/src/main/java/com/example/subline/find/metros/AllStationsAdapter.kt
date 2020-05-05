package com.example.subline.find.metros

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_station_item.view.*

class AllStationsAdapter (val stations : List<String>,val pictoline : Int,val metro : String) : RecyclerView.Adapter<AllStationsAdapter.MetrosViewHolder>() {

    class MetrosViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetrosViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent, false)

        return MetrosViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = "$stat"

        holder.statView.setOnClickListener {
            val intent= Intent(it.context,HoraireMetro::class.java)
            intent.putExtra("station",stat)
            intent.putExtra("pictoline",pictoline)
            intent.putExtra("line",metro)
            //intent.putExtra("direct1",stations[0])
            //intent.putExtra("direct2",stations.last())
            it.context.startActivity(intent)
            true
        }

    }

}
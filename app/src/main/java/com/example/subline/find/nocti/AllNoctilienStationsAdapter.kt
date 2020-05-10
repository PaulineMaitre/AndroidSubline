package com.example.subline.find.nocti

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_station_item.view.*

class AllNoctilienStationsAdapter (val stations: List<String>, val pictoline: Int, val noctilien: String): RecyclerView.Adapter<AllNoctilienStationsAdapter.NoctiliensViewHolder>() {

    class NoctiliensViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoctiliensViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return NoctiliensViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoctiliensViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, HoraireNoctilien::class.java)
            intent.putExtra("station", stat)
            intent.putExtra("pictoline", pictoline)
            intent.putExtra("line", noctilien)
            it.context.startActivity(intent)
            true
        }

    }

}
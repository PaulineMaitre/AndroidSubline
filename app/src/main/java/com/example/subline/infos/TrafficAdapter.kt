package com.example.subline.infos

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.TrafficResult
import kotlinx.android.synthetic.main.list_traffic_item.view.*

class TrafficAdapter (var transports : MutableList<TrafficResult.Transport>) : RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder>() {

    class TrafficViewHolder(val trafficView: View) : RecyclerView.ViewHolder(trafficView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_traffic_item, parent, false)
        return TrafficViewHolder(view)
    }

    override fun getItemCount(): Int = transports.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: TrafficViewHolder, position: Int) {

        Log.d("HII","REFRESH $transports")

          holder.trafficView.lineTrafficTextView.text = transports[position].type
          holder.trafficView.trafficStateResultTextView.text = transports[position].title
          holder.trafficView.trafficMessageTextView.text = transports[position].message
          holder.trafficView.logoTrafficImageView.setImageResource(transports[position].picto)
        if(transports[position].title == "Trafic normal"){
            holder.trafficView.trafficStateImageView.setImageResource(R.drawable.tick)
        }else if(transports[position].title == "Travaux"){
            holder.trafficView.trafficStateImageView.setImageResource(R.drawable.maintenance)
        }else if(transports[position].title == "Incidents" || transports[position].title == "Incidents techniques"){
            holder.trafficView.trafficStateImageView.setImageResource(R.drawable.noentry)
        }
    }



}










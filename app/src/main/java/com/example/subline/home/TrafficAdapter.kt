package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.TrafficResult
import kotlinx.android.synthetic.main.list_traffic_item.view.*

class TrafficAdapter (val transports : List<TrafficResult.Metro>) : RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder>() {

    var type : String = "Metro"

    class TrafficViewHolder(val trafficView: View) : RecyclerView.ViewHolder(trafficView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_traffic_item, parent, false)

        return TrafficViewHolder(view)
    }

    override fun getItemCount(): Int = transports.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: TrafficViewHolder, position: Int) {
        if(position > 15 && position <21){
            type = "RER"
        } else if (position >= 21){
            type = "Tramway"
        }
          holder.trafficView.lineTrafficTextView.text = "$type ${transports[position].line}"
          holder.trafficView.trafficStateResultTextView.text = transports[position].title
          holder.trafficView.trafficMessageTextView.text = transports[position].message
    }

}










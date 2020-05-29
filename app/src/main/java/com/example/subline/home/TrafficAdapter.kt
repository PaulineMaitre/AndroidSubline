package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.TrafficResult
import com.example.subline.find.metros.Station
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.list_favoris_item.view.*
import kotlinx.android.synthetic.main.list_traffic_item.view.*
import kotlinx.coroutines.runBlocking

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
        }else if (position >= 21){
            type = "Tramway"
        }
          holder.trafficView.tv_ligne_traffic.text = "$type ${transports[position].line}"
          holder.trafficView.tv_etat_traffic.text = transports[position].title
          holder.trafficView.tv_message_traffic.text = transports[position].message
    }

}










package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.Station
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.list_favoris_item.view.*
import kotlinx.coroutines.runBlocking

class FavorisAdapter (val favoris : List<Station>, val favScheduleRecyclerView : RecyclerView, val scheduleTextView : TextView) : RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder>() {


    class FavorisViewHolder(val favView : View) : RecyclerView.ViewHolder(favView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FavorisViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_favoris_item, parent, false)

        return FavorisViewHolder(view)
    }

    override fun getItemCount(): Int  = favoris.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {
        val favori = favoris[position]
        holder.favView.favoris_station.text = favori.name
        holder.favView.favoris_direction.text = favori.direction_name
        holder.favView.lineName.setImageResource(favori.picto_ligne)

        val transportType = favori.type

        holder.favView.setOnClickListener {

                var scheduleList = arrayListOf<String>()
                var i = 0
                val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
                runBlocking {
                    val results = service.getSchedules(transportType, favori.ligne_name, favori.name, favori.way)
                    results.result.schedules.map {
                        if(i<2){
                            scheduleList.add(it.message)
                            i++
                        }
                    }
                    scheduleTextView.isVisible = true
                    favScheduleRecyclerView.adapter = HoraireFavAdapter(scheduleList, favori.direction_name)
                }

       }

    }

}










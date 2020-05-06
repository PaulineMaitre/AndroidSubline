package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.metros.Station
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.list_favoris_item.view.*
import kotlinx.coroutines.runBlocking

class FavorisAdapter (val favoris : List<Station>, val rv : RecyclerView, val tv : TextView) : RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder>() {


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

        holder.favView.setOnClickListener {

                var listhoraire = arrayListOf<String>()
                var i = 0
                val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
                runBlocking {
                    val results = service.getSchedules(TYPE_METRO, favori.ligne_name, favori.name, favori.way)
                    results.result.schedules.map {
                        if(i<2){
                            listhoraire.add(it.message)
                            i++
                        }
                    }
                    tv.isVisible = true
                    rv.adapter = HoraireFavAdapter(listhoraire,favori.direction_name)
                }

       }

    }

}










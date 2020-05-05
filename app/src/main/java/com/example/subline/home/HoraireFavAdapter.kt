package com.example.subline.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.metros.Station
import com.example.subline.service.RatpService
import com.example.subline.service.Stations
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.android.synthetic.main.list_favoris_item.view.*
import kotlinx.android.synthetic.main.list_horairem_item.view.*
import kotlinx.coroutines.runBlocking

class HoraireFavAdapter (val horaires: List<String>, val direction: String) : RecyclerView.Adapter<HoraireFavAdapter.FavorisViewHolder>() {

    class FavorisViewHolder(val favView: View) : RecyclerView.ViewHolder(favView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorisViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_horairem_item, parent,false)

        return FavorisViewHolder(view)
    }

    override fun getItemCount(): Int  = horaires.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {
        val horaire = horaires[position]
        holder.favView.horaire_list_time.text = horaire
        holder.favView.horaire_list_destination.text = direction

    }

    }










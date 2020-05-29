package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_horaire_item.view.*

class HoraireFavAdapter (val horaires: List<String>, val direction: String) : RecyclerView.Adapter<HoraireFavAdapter.FavorisViewHolder>() {

    class FavorisViewHolder(val favView: View) : RecyclerView.ViewHolder(favView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorisViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_horaire_item, parent,false)

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










package com.example.subline.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_horaire_item.view.*

class HoraireFavAdapter (private val scheduleList: List<String>, private val direction: String) : RecyclerView.Adapter<HoraireFavAdapter.FavorisViewHolder>() {

    class FavorisViewHolder(val favView: View) : RecyclerView.ViewHolder(favView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorisViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_horaire_item, parent,false)

        return FavorisViewHolder(view)
    }

    override fun getItemCount(): Int  = scheduleList.size

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.favView.horaire_list_time.text = schedule
        holder.favView.horaire_list_destination.text = direction
    }
}










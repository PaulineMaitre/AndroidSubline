package com.example.subline.find.tram

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_horairem_item.view.*

class HoraireTramAdapter(val times: List<String>, var destinations: List<String>): RecyclerView.Adapter<HoraireTramAdapter.MetrosViewHolder>() {

    class MetrosViewHolder(val horaireView: View): RecyclerView.ViewHolder(horaireView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MetrosViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_horairem_item, parent, false)

        return MetrosViewHolder(view)
    }

    override fun getItemCount(): Int = times.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
        var time = times[position]
        var destination = destinations[position]

        holder.horaireView.horaire_list_time.text = time
        holder.horaireView.horaire_list_destination.text = destination
       }

    }










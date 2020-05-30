package com.example.subline.find.findResults

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.list_horaire_item.view.*

class ScheduleAdapter(val times: List<String>, var destinations: List<String>): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(val scheduleView: View): RecyclerView.ViewHolder(scheduleView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_horaire_item, parent, false)

        return ScheduleViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = times.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        var time = times[position]
        var destination = destinations[position]

        holder.scheduleView.horaire_list_time.text = time
        holder.scheduleView.horaire_list_destination.text = destination
       }

    }










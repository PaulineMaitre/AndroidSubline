package com.example.subline.ui.find.metros

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import kotlinx.android.synthetic.main.fragment_find_metros.view.*
import kotlinx.android.synthetic.main.list_metro_item.view.*

class AllMetrosAdapter (val metros : List<Int>) : RecyclerView.Adapter<AllMetrosAdapter.MetrosViewHolder>() {

    class MetrosViewHolder(val metrosView : View) : RecyclerView.ViewHolder(metrosView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MetrosViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent, false)
        return MetrosViewHolder(view)
    }

    override fun getItemCount(): Int  = metros.size


    override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
        var metro = metros[position]
        Log.d("CRC","$metro")
        holder.metrosView.metro_name.text = "$metro"

       }



}
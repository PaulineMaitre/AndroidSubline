package com.example.subline.ui.find.metros

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpPictoService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.retrofit
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.android.synthetic.main.fragment_find_metros.view.*
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.android.synthetic.main.list_station_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AllStationsAdapter (val stations : List<String>) : RecyclerView.Adapter<AllStationsAdapter.MetrosViewHolder>() {

    class MetrosViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetrosViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent, false)

        return MetrosViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = "$stat"

    }

}
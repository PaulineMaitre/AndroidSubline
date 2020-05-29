package com.example.subline.find.tram

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_NOCTI
import com.example.subline.utils.TYPE_TRAM
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.list_station_item.view.*
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

class AllTramStationsAdapter (val stations: List<String>, val pictoline: Int, val tram: String): RecyclerView.Adapter<AllTramStationsAdapter.TramsViewHolder>() {

    class TramsViewHolder(val statView: View) : RecyclerView.ViewHolder(statView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramsViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_station_item, parent,false)

        return TramsViewHolder(view)
    }

    override fun getItemCount(): Int = stations.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TramsViewHolder, position: Int) {
        var stat = stations[position]
        holder.statView.station_name.text = stat

        holder.statView.setOnClickListener {
            val intent= Intent(it.context, HoraireTram::class.java)
            val destinations = getDestinations(it.context, tram)
            Log.d("EPF", "dest1 $destinations")
            Log.d("EPF", "size=${destinations.size}")
            if(destinations.size != 0) {
                intent.putStringArrayListExtra("destinations", destinations)
                intent.putExtra("station", stat)
                intent.putExtra("pictoline", pictoline)
                intent.putExtra("line", tram)
                it.context.startActivity(intent)
                true
            }
        }
    }

    fun getDestinations(context: Context, tram: String): ArrayList<String> {
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var listDestinations = arrayListOf<String>()
        try {
            runBlocking {
                val results = service.getDestinations(TYPE_TRAM, tram)
                val direct1 = results.result.destinations[0].name
                listDestinations.add(direct1)
                if (results.result.destinations.size > 1) {
                    val direct2 = results.result.destinations[1].name
                    listDestinations.add(direct2)
                }
            }
        } catch (e: retrofit2.HttpException) {
            Toast.makeText(context, R.string.scheduleError, Toast.LENGTH_SHORT).show()
        }
        return listDestinations
    }

}
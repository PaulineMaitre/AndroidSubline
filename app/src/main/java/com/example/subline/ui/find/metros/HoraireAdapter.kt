package com.example.subline.ui.find.metros

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.list_horairem_item.view.*
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class HoraireAdapter (val times : List<String>, var destinations : List<String>) : RecyclerView.Adapter<HoraireAdapter.MetrosViewHolder>() {

    class MetrosViewHolder(val horaireView : View) : RecyclerView.ViewHolder(horaireView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MetrosViewHolder {
        val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInfater.inflate(R.layout.list_horairem_item, parent, false)

        return MetrosViewHolder(view)
    }

    override fun getItemCount(): Int  = times.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
        var time = times[position]
        var destination = destinations[position]

        holder.horaireView.horaire_list_time.text = "$time"
        holder.horaireView.horaire_list_destination.text = "$destination"
       }

    }










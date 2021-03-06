package com.example.subline.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.subline.R
import com.example.subline.data.FavorisDao
import com.example.subline.find.Station
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.example.subline.data.AppDatabase
import com.example.subline.find.findResults.ScheduleAdapter
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.list_favoris_item.view.*
import kotlinx.android.synthetic.main.list_favoris_item.view.deleteFavButton
import kotlinx.android.synthetic.main.list_favoris_item.view.favStation
import kotlinx.android.synthetic.main.list_favoris_item.view.favoris_direction
import kotlinx.android.synthetic.main.list_favoris_item.view.lineIcon
import kotlinx.coroutines.runBlocking

class FavorisAdapter (private val favoris : MutableList<Station>, private val favScheduleRecyclerView : RecyclerView, private val scheduleTextView : TextView, private val listMarker : ArrayList<Marker>, private val layout_nofavoris : RelativeLayout) : RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder>() {

    private var favDao : FavorisDao? = null
    private lateinit var context: Context
    private var checkedposition : Int = -1

    class FavorisViewHolder(val favView : View) : RecyclerView.ViewHolder(favView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FavorisViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_favoris_item, parent, false)

        val databasesaved =
            Room.databaseBuilder(parent.context, AppDatabase::class.java, "favoris")
                .build()

         favDao = databasesaved.getFavorisDao()

        context = parent.context

        return FavorisViewHolder(view)
    }

    override fun getItemCount(): Int  = favoris.size


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {

        val favori = favoris[position]
        holder.favView.favStation.text = favori.name
        holder.favView.favoris_direction.text = favori.direction_name
        holder.favView.lineIcon.setImageResource(favori.picto_ligne)

        val transportType = favori.type

        if(checkedposition == position){
            holder.favView.carview_fav.backgroundTintList = context.resources!!.getColorStateList(R.color.buttonPressed)
        }else{
            holder.favView.carview_fav.backgroundTintList = context.resources!!.getColorStateList(R.color.Blank)
        }

        holder.favView.deleteFavButton.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(context.getString(R.string.titleDeleteAlert))
                setMessage("Etes-vous sur de vouloir supprimer ${favori.name} ?")
                setNegativeButton(android.R.string.no){_,_ -> }
                setPositiveButton(android.R.string.yes){_,_ ->
                    favoris.removeAt(position)
                    runBlocking {
                        favDao!!.deleteStation(favori)
                    }
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, favoris.size)
                    val marker = listMarker[position]
                    marker.remove()
                    if(favoris.isEmpty()){
                        layout_nofavoris.visibility = View.VISIBLE
                    }
                }
                show()
            }
        }

        holder.favView.setOnClickListener {
            notifyItemChanged(checkedposition)
            checkedposition = position
            notifyItemChanged(checkedposition)

            var scheduleList = arrayListOf<String>()
            var destinations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            runBlocking {
                val results = service.getSchedules(transportType, favori.ligne_name, favori.name, favori.way)
                results.result.schedules.map {
                    scheduleList.add(it.message)
                    destinations.add(it.destination)
                }
                scheduleTextView.isVisible = true
                favScheduleRecyclerView.adapter = ScheduleAdapter(scheduleList, destinations)
            }
       }
    }
}










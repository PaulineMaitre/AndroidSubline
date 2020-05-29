package com.example.subline.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.subline.R
import com.example.subline.find.Station
import com.example.subline.data.TrafficResult
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import com.example.tripin.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

     override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
         val rv_fav : RecyclerView = root.findViewById(R.id.home_favoris_rv)
         val tv_horaires : TextView = root.findViewById(R.id.tv_horaires)
         var favList : List<Station> = emptyList()
         tv_horaires.isVisible = false
         val rv2 = root.findViewById<RecyclerView>(R.id.home_horaire)
         val fab : FloatingActionButton = root.findViewById(R.id.fab_qrcode)
         val rv3 = root.findViewById<RecyclerView>(R.id.rv_home_traffic)

         val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

         val database =
             Room.databaseBuilder(activity!!.baseContext, AppDatabase::class.java, "favoris")
                 .build()
         val favorisDao = database.getFavorisDao()

         runBlocking {
              favList = favorisDao.getStation()
         }

        rv_fav.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         rv2.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

         rv_fav.adapter = FavorisAdapter(favList.asReversed(), rv2, tv_horaires)

         val list_traffic = arrayListOf<TrafficResult.Metro>()

         runBlocking {
             val results = service.getTrafficInfoC()
             results.result.metros.map {
                 val metro = TrafficResult.Metro(it.line,it.slug,it.title,it.message)
                 list_traffic.add(metro)
             }
             results.result.rers.map {
                 val rer = TrafficResult.Metro(it.line,it.slug,it.title,it.message)
                 list_traffic.add(rer)
             }
             results.result.tramways.map {
                 val tram = TrafficResult.Metro(it.line,it.slug,it.title,it.message)
                 list_traffic.add(tram)
             }
         }

         rv3.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
         rv3.adapter = TrafficAdapter(list_traffic)

         bt_api.setOnClickListener { view ->
             val intent = Intent(this.context, AppelApi::class.java)
             startActivity(intent)
             true

         }
         fab.setOnClickListener {view ->
             val intent = Intent(this.context, QRCode::class.java)
             startActivity(intent)
             true
         }




         return root
    }


}

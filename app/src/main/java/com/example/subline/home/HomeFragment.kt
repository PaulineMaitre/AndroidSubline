package com.example.subline.home

import android.content.Intent
import android.os.Bundle
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
         val favListRecyclerView : RecyclerView = root.findViewById(R.id.favHomeRecyclerView)
         val scheduleTextView : TextView = root.findViewById(R.id.nextScheduleTitle)
         var favList : List<Station> = emptyList()
         scheduleTextView.isVisible = false
         val favScheduleRecyclerView = root.findViewById<RecyclerView>(R.id.favScheduleRecyclerView)
         val fabQRCode : FloatingActionButton = root.findViewById(R.id.fabQRcode)
         val homeTrafficRecyclerView = root.findViewById<RecyclerView>(R.id.homeTrafficRecyclerView)

         val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

         val database =
             Room.databaseBuilder(activity!!.baseContext, AppDatabase::class.java, "favoris")
                 .build()
         val favorisDao = database.getFavorisDao()

         runBlocking {
              favList = favorisDao.getStation()
         }

        favListRecyclerView.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         favScheduleRecyclerView.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

         favListRecyclerView.adapter = FavorisAdapter(favList.asReversed(), favScheduleRecyclerView, scheduleTextView)

         val listTraffic = arrayListOf<TrafficResult.Metro>()

         runBlocking {
             val results = service.getTrafficInfoC()
             results.result.metros.map {
                 val metro = TrafficResult.Metro(it.line, it.slug, it.title, it.message)
                 listTraffic.add(metro)
             }
             results.result.rers.map {
                 val rer = TrafficResult.Metro(it.line, it.slug, it.title, it.message)
                 listTraffic.add(rer)
             }
             results.result.tramways.map {
                 val tram = TrafficResult.Metro(it.line, it.slug, it.title, it.message)
                 listTraffic.add(tram)
             }
         }

         homeTrafficRecyclerView.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
         homeTrafficRecyclerView.adapter = TrafficAdapter(listTraffic)

         fabQRCode.setOnClickListener { view ->
             val intent = Intent(this.context, QRCode::class.java)
             startActivity(intent)
             true
         }
         return root
    }


}

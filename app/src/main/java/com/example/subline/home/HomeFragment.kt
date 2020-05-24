package com.example.subline.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.subline.AppelApi
import com.example.subline.R
import com.example.subline.find.metros.Station
import com.example.tripin.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

     override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
         val rv : RecyclerView = root.findViewById(R.id.home_favoris)
         val tv : TextView = root.findViewById(R.id.tv_horaires)
         var list_fav : List<Station> = emptyList()
         tv.isVisible = false
         val rv2 = root.findViewById<RecyclerView>(R.id.home_horaire)
         val fab : FloatingActionButton = root.findViewById(R.id.fab_qrcode)

         val database =
             Room.databaseBuilder(activity!!.baseContext, AppDatabase::class.java, "favoris")
                 .build()
         val favorisDao = database.getFavorisDao()

         runBlocking {
              list_fav = favorisDao.getStation()
         }

        rv.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         rv2.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

         rv.adapter = FavorisAdapter(list_fav, rv2, tv)
         fab.setOnClickListener {view ->
             Log.d("CCC","YES")
             val intent = Intent(this.context, QRCode::class.java)
             startActivity(intent)
             true
         }

         return root
    }


}

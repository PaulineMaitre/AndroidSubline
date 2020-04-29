package com.example.subline.find.metros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.subline.R
import com.example.subline.data.FavorisDao
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.example.tripin.data.AppDatabase
import kotlinx.android.synthetic.main.activity_horaire_metro.*
import kotlinx.coroutines.runBlocking

class HoraireMetro : AppCompatActivity() {

    var favoris : Boolean = false
    private var favorisDao: FavorisDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horaire_metro)

        // ON APPELLE LA BDD
        val database =
            Room.databaseBuilder(this, AppDatabase::class.java, "favoris")
                .build()
        favorisDao = database.getFavorisDao()

        rv_horaire_station.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val line = intent.getStringExtra("line")
        val pictoline = intent.getIntExtra("pictoline",0)
        val station_name = intent.getStringExtra("station")
        val direct1 = intent.getStringExtra("direct1")
        val direct2 = intent.getStringExtra("direct2")
        var direction_choisi = direct1
        horaire_ligne.setImageResource(pictoline)
        horaire_station.text = "$station_name"
        radio_direct1.text = "$direct1"
        radio_direct2.text = "$direct2"
        var way = "R"
        var id_direction = 1

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        recherhe_match_stationfav(station_name,line,direct1,"metro")


        recyclerview_horaire(service,station_name,line,way)


        radiogroup_direction.setOnCheckedChangeListener { group, checkedId ->

            if(radio_direct1.isChecked) {
                way = "R"
                direction_choisi = direct1
                id_direction = 1

            }else if (radio_direct2.isChecked){
                way = "A"
                direction_choisi = direct2
                id_direction = 2
            }
            runBlocking {
                recherhe_match_stationfav(station_name,line,direction_choisi,"metro")
                recyclerview_horaire(service,station_name,line,way)
            }
        }


        fab_fav.setOnClickListener {
            gestion_btn_favoris(station_name,line,direction_choisi,pictoline,"metro",way)
        }





    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean =
        when(item.itemId){
            android.R.id.home -> {
                finish()
                true

            }
            else -> super.onOptionsItemSelected(item)
        }

    fun gestion_btn_favoris(station_name : String,line : String,direction : String,pictoline : Int,type : String,way : String){
        val stat : Station = Station(0,station_name,type,line,direction,way,pictoline)
        if(favoris == false){
            fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            Toast.makeText(this, "Le métro a bien été ajouté aux favoris", Toast.LENGTH_SHORT).show()
            favoris = true
            runBlocking {
                favorisDao?.addStation(stat)
            }

        }else {
            fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            Toast.makeText(this, "Le métro a bien été supprimé des favoris", Toast.LENGTH_SHORT).show()
            favoris = false
            runBlocking {
                favorisDao?.deleteStation(favorisDao?.getStation(station_name,direction,type)!!)
            }
        }
    }

    fun recherhe_match_stationfav(station_name : String,line : String,direction : String,type : String){
        runBlocking {
            if (favorisDao?.getStation(station_name, direction, type) == null) {
                favoris = false
                fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            } else {
                favoris = true
                fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            }
        }

    }

    fun recyclerview_horaire(service: RatpService,station_name : String,line : String,way : String){

        var time = arrayListOf<String>()
        var destinations = arrayListOf<String>()

        runBlocking {
            val results = service.getSchedules("metros", line, station_name, way)
            results.result.schedules.map {
                time.add(it.message)
                destinations.add(it.destination)
                rv_horaire_station.adapter = HoraireAdapter(time,destinations)
            }
        }
    }

}

package com.example.subline.find.rers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.subline.R
import com.example.subline.data.FavorisDao
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import com.example.tripin.data.AppDatabase
import kotlinx.android.synthetic.main.activity_horaire_metro.*
import kotlinx.coroutines.runBlocking

class HoraireRer: AppCompatActivity() {

    var favoris: Boolean = false
    private var favorisDao: FavorisDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horaire_metro)

        radio_direct3.isVisible = false
        radio_direct4.isVisible = false
        radio_direct5.isVisible = false
        radio_direct6.isVisible = false
        radio_direct7.isVisible = false


        // ON APPELLE LA BDD
        val database =
            Room.databaseBuilder(this, AppDatabase::class.java, "favoris")
                .build()
        favorisDao = database.getFavorisDao()

        rv_horaire_station.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val line: String = intent.getStringExtra("line")
        val pictoline = intent.getIntExtra("pictoline",0)
        val stationName: String = intent.getStringExtra("station")

        horaire_ligne.setImageResource(pictoline)
        horaire_station.text = stationName

        var direct1 = "A"
        var direct2 = "R"
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        if(line != "C" && line != "D") {
            runBlocking {
                val results = service.getDestinations(TYPE_RER, line)
                direct1 = results.result.destinations[1].name
                direct2 = results.result.destinations[0].name
                radio_direct1.text = direct1
                radio_direct2.text = direct2
            }
        } else {
            specialCasesRerCandD(line)
        }

        var direction_choisie = direct1

        var way = "A"
        //recherche_match_stationfav(stationName, line, direct1, TYPE_RER)

        recyclerview_horaire(service, stationName, line, way)


        radiogroup_direction.setOnCheckedChangeListener { group, checkedId ->

            if(radio_direct1.isChecked) {
                way = "A"
                direction_choisie = direct1

            } else if (radio_direct2.isChecked){
                way = "R"
                direction_choisie = direct2
            }

            runBlocking {
                //recherche_match_stationfav(stationName, line, direction_choisie, TYPE_RER)
                recyclerview_horaire(service, stationName, line, way)
            }
        }

        /*fab_fav.setOnClickListener {
            gestion_btn_favoris(stationName, line, direction_choisie, pictoline, TYPE_RER, way)
        }*/


    }

    private fun specialCasesRerCandD(line: String) {
        radio_direct3.isVisible = true
        radio_direct4.isVisible = true
        radio_direct5.isVisible = true
        radio_direct6.isVisible = true

        if(line == "C") {
            radio_direct7.isVisible = true
            radio_direct1.text = directC1
            radio_direct2.text = directC2
            radio_direct3.text = directC3
            radio_direct4.text = directC4
            radio_direct5.text = directC5
            radio_direct6.text = directC6
            radio_direct7.text = directC7
        } else { //if line = "D"
            radio_direct1.text = directD1
            radio_direct2.text = directD2
            radio_direct3.text = directD3
            radio_direct4.text = directD4
            radio_direct5.text = directD5
            radio_direct6.text = directD6
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    /*fun gestion_btn_favoris(station_name: String, line: String, direction: String, pictoline: Int, type: String, way: String){
        val stat : Station = Station(0, station_name, type, line, direction, way, pictoline)
        if(!favoris){
            fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            Toast.makeText(this, R.string.toastRerAddToFav, Toast.LENGTH_SHORT).show()
            favoris = true
            runBlocking {
                favorisDao?.addStation(stat)
            }

        }else {
            fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            Toast.makeText(this, R.string.toastRerDeleteFromFav, Toast.LENGTH_SHORT).show()
            favoris = false
            runBlocking {
                favorisDao?.deleteStation(favorisDao?.getStation(station_name, direction, type)!!)
            }
        }
    }

    fun recherche_match_stationfav(station_name: String, line: String, direction: String, type: String){
        runBlocking {
            if (favorisDao?.getStation(station_name, direction, type) == null) {
                favoris = false
                fab_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            } else {
                favoris = true
                fab_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            }
        }

    }*/

    fun recyclerview_horaire(service: RatpService, station_name: String, line: String, way: String){

        var time = arrayListOf<String>()
        var destinations = arrayListOf<String>()

        if(line != "C" && line != "D") {
            runBlocking {
                val results = service.getSchedules(TYPE_RER, line, station_name, way)
                results.result.schedules.map {
                    time.add(it.message)
                    destinations.add(it.destination)
                    rv_horaire_station.adapter = HoraireRerAdapter(time, destinations)
                }
            }
        } else {
            destinations.add("Destination unavailable")

            time.add("Schedules unavailable")
            rv_horaire_station.adapter = HoraireRerAdapter(time, destinations)
        }
    }

}
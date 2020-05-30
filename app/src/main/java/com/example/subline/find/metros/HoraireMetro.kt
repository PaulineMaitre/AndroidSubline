package com.example.subline.find.metros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.subline.R
import com.example.subline.data.FavorisDao
import com.example.subline.find.Station
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.example.tripin.data.AppDatabase
import kotlinx.android.synthetic.main.activity_horaire.*
import kotlinx.coroutines.runBlocking

class HoraireMetro: AppCompatActivity() {

    var favoris: Boolean = false
    private var favorisDao: FavorisDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horaire)

        direction1RadioButton.isVisible = true
        direction2RadioButton.isVisible = false

        // ON APPELLE LA BDD
        val database =
            Room.databaseBuilder(this, AppDatabase::class.java, "favoris")
                .build()
        favorisDao = database.getFavorisDao()

        scheduleRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val line = intent.getStringExtra("line")
        val pictoLine = intent.getIntExtra("pictoline",0)
        val stationName = intent.getStringExtra("station")
        val destinations = intent.getStringArrayListExtra("destinations")
        val transportType = intent.getStringExtra("transportType")

        lineImageView.setImageResource(pictoLine)
        stationNameTextView.text = stationName

        var way = "A"
        var direct2 = "R"
        var direct1 = destinations[0]
        var favDirection = direct1
        if(destinations.size > 1) {
            direct2 = destinations[1]
            direction2RadioButton.isVisible = true
        }
        if(line == "14") { // fix bug A/R on line 14
            direction1RadioButton.text = direct2
            direction2RadioButton.text = direct1
            favDirection = direct2
        } else {
            direction1RadioButton.text = direct1
            direction2RadioButton.text = direct2
        }

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

        searchMathFavStation(stationName, line, favDirection, transportType)

        getLineSchedule(service, transportType, stationName, line, way)


        directionRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(direction1RadioButton.isChecked) {
                way = "A"
                favDirection = direct1

            } else if (direction2RadioButton.isChecked){
                way = "R"
                favDirection = direct2
            }
            if(line == "14") { // fix bug A/R on line 14
                favDirection = if(favDirection == direct1) {
                    direct2
                } else {
                    direct1
                }
            }
            runBlocking {
                searchMathFavStation(stationName, line, favDirection, transportType)
                getLineSchedule(service, transportType, stationName, line, way)
            }
        }

        favButton.setOnClickListener {
            pushFavButton(stationName, line, favDirection, pictoLine, transportType, way)
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

    private fun pushFavButton(stationName: String, line: String, direction: String, pictoLine: Int, type: String, way: String) {
        val locStat = retrofit(BASE_URL_PICTO).create(RatpPictoService::class.java)
        var lat = 0.0
        var long = 0.0
        runBlocking {
            val result = locStat.getLoc(stationName)
            lat = result.records[0].fields.stop_coordinates[0]
            long = result.records[0].fields.stop_coordinates[1]
        }
        val stat = Station(0, stationName, type, line, direction, way, pictoLine,lat,long)

        if(!favoris) {
            favButton.setImageResource(R.drawable.ic_favorite_black_24dp)
            Toast.makeText(this, R.string.toastMetroAddToFav, Toast.LENGTH_SHORT).show()
            favoris = true
            runBlocking {
                favorisDao?.addStation(stat)
            }
        } else {
            favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            Toast.makeText(this, R.string.toastMetroDeleteFromFav, Toast.LENGTH_SHORT).show()
            favoris = false
            runBlocking {
                favorisDao?.deleteStation(favorisDao?.getStation(stationName, direction, type)!!)
            }
        }
    }

    private fun searchMathFavStation(station_name: String, line: String, direction: String, type: String){
        runBlocking {
            if (favorisDao?.getStation(station_name, direction, type) == null) {
                favoris = false
                favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            } else {
                favoris = true
                favButton.setImageResource(R.drawable.ic_favorite_black_24dp)
            }
        }

    }

    private fun getLineSchedule(service: RatpService, transportType: String, stationName: String, line: String, way: String) {

        var time = arrayListOf<String>()
        var destinations = arrayListOf<String>()

        runBlocking {
            val results = service.getSchedules(transportType, line, stationName, way)
            results.result.schedules.map {
                time.add(it.message)
                destinations.add(it.destination)
                scheduleRecyclerView.adapter = HoraireMetroAdapter(time, destinations)
            }
        }
    }

}

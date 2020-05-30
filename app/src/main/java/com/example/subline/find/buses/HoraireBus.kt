package com.example.subline.find.buses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.subline.utils.*
import com.example.tripin.data.AppDatabase
import kotlinx.android.synthetic.main.activity_horaire.*
import kotlinx.coroutines.runBlocking

class HoraireBus: AppCompatActivity() {

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
        val pictoline = intent.getIntExtra("pictoline",0)
        val stationName = intent.getStringExtra("station")
        val destinations = intent.getStringArrayListExtra("destinations")

        lineImageView.setImageResource(pictoline)
        stationNameTextView.text = stationName

        var direct2 = "R"
        val direct1 = destinations[0]
        direction1RadioButton.text = direct1
        if(destinations.size > 1) {
            direct2 = destinations[1]
            direction2RadioButton.isVisible = true
            direction2RadioButton.text = direct2
        }
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

        var favDirection = direct1

        var way = "A"
        searchMatchFavStation(stationName, line, favDirection, TYPE_BUS)

        getLineSchedule(service, stationName, line, way)

        directionRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(direction1RadioButton.isChecked) {
                way = "A"
                favDirection = direct1

            } else if (direction2RadioButton.isChecked){
                way = "R"
                favDirection = direct2
            }

            runBlocking {
                searchMatchFavStation(stationName, line, favDirection, TYPE_BUS)
                getLineSchedule(service, stationName, line, way)
            }
        }

        favButton.setOnClickListener {
            pushFavButton(stationName, line, favDirection, pictoline, TYPE_BUS, way)
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

    private fun pushFavButton(station_name: String, line: String, direction: String, pictoline: Int, type: String, way: String){
        val locStat = retrofit(BASE_URL_PICTO).create(RatpPictoService::class.java)
        var lat = 0.0
        var long = 0.0
        runBlocking {
            val result = locStat.getLoc(station_name)
            lat = result.records[0].fields.stop_coordinates[0]
            long = result.records[0].fields.stop_coordinates[1]
        }
        val stat = Station(0, station_name, type, line, direction, way, pictoline,lat,long)
        if(!favoris){
            favButton.setImageResource(R.drawable.ic_favorite_black_24dp)
            Toast.makeText(this, R.string.toastBusAddToFav, Toast.LENGTH_SHORT).show()
            favoris = true
            runBlocking {
                favorisDao?.addStation(stat)
            }

        } else {
            favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            Toast.makeText(this, R.string.toastBusDeleteFromFav, Toast.LENGTH_SHORT).show()
            favoris = false
            runBlocking {
                favorisDao?.deleteStation(favorisDao?.getStation(station_name, direction, type)!!)
            }
        }
    }

    private fun searchMatchFavStation(station_name: String, line: String, direction: String, type: String){
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

    private fun getLineSchedule(service: RatpService, station_name: String, line: String, way: String){

        var time = arrayListOf<String>()
        var destinations = arrayListOf<String>()

        runBlocking {
            val results = service.getSchedules(TYPE_BUS, line, station_name, way)

            results.result.schedules.map {
                time.add(it.message)
                destinations.add(it.destination)
                scheduleRecyclerView.adapter = HoraireBusAdapter(time, destinations)
            }
        }

    }

}

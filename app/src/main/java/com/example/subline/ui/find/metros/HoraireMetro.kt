package com.example.subline.ui.find.metros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.android.synthetic.main.activity_horaire_metro.*
import kotlinx.coroutines.runBlocking

class HoraireMetro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horaire_metro)

        rv_horaire_station.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val line = intent.getStringExtra("line")
        val pictoline = intent.getIntExtra("pictoline",0)
        val station = intent.getStringExtra("station")
        val direct1 = intent.getStringExtra("direct1")
        val direct2 = intent.getStringExtra("direct2")
        horaire_ligne.setImageResource(pictoline)
        horaire_station.text = "$station"
        radio_direct1.text = "$direct1"
        radio_direct2.text = "$direct2"
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

        var time = arrayListOf<String>()
        var destinations = arrayListOf<String>()

        runBlocking {
            val results = service.getSchedules("metros", line, station, "A+R")
            results.result.schedules.map {
                time.add(it.message)
                destinations.add(it.destination)
                rv_horaire_station.adapter = HoraireAdapter(time,destinations)
            }
        }

        radiogroup_direction.setOnCheckedChangeListener { group, checkedId ->
            var way = "A+R"
            var time = arrayListOf<String>()
            var destinations = arrayListOf<String>()
            if(radio_direct1.isChecked){
                way = "R"
            }else if (radio_direct2.isChecked){
                way = "A"
            }
            Log.d("CCC","$checkedId")
            runBlocking {
                val results = service.getSchedules("metros", line, station, way)
                results.result.schedules.map {
                    time.add(it.message)
                    destinations.add(it.destination)
                    rv_horaire_station.adapter = HoraireAdapter(time,destinations)
                }
            }
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
}

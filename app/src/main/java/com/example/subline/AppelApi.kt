package com.example.subline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.subline.service.RATPService
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.coroutines.runBlocking

class AppelApi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appel_api)

        var resultToPrint = ""
        var request = ""

        lineInput.isVisible = false
        stationInput.isVisible = false
        btnSearch.isVisible = false

        //print all lines to initialize activity
        val service = retrofit().create(RATPService::class.java)
        runBlocking {
            val results = service.getAllLines("metros")
            Log.d("EPF", "test $results")
            results.result.metros.map {
                val name = it.name
                //val directions = it.directions
                resultToPrint += "$name \n"
                resultTextView.text = resultToPrint
            }
        }

        btnAllLines.setOnClickListener() {
            resultToPrint = ""
            request = "getAllLines"

            lineInput.isVisible = false
            stationInput.isVisible = false
            btnSearch.isVisible = false
            resultTextView.isVisible = true

            val service = retrofit().create(RATPService::class.java)
            runBlocking {
                val results = service.getAllLines("metros")
                Log.d("EPF", "test $results")
                results.result.metros.map {
                    val name = it.name
                    //val directions = it.directions
                    resultToPrint += "$name \n"
                    resultTextView.text = resultToPrint
                }
            }
        }

        btnLineInfo.setOnClickListener {
            resultToPrint = ""
            request = "getLineInfo"

            resultTextView.isVisible = false
            lineInput.isVisible = true
            //lineInput.isInvisible = true
            stationInput.isVisible = false
            btnSearch.isVisible = true
        }

        btnSchedules.setOnClickListener {
            resultToPrint = ""
            request = "getSchedules"

            resultTextView.isVisible = false
            lineInput.isVisible = true
            stationInput.isVisible = true
            btnSearch.isVisible = true
        }

        btnStations.setOnClickListener {
            resultToPrint = ""
            request = "getStations"

            resultTextView.isVisible = false
            lineInput.isVisible = true
            //lineInput.isInvisible = true
            stationInput.isVisible = false
            btnSearch.isVisible = true
        }

        btnSearch.setOnClickListener() {
            resultToPrint = ""

            resultTextView.isVisible = true

            val line = lineInput.text.toString()

            val service = retrofit().create(RATPService::class.java)

            when(request) {
                "getLineInfo" -> {
                    runBlocking {
                        val result = service.getLineInfo("metros", line)
                        Log.d("EPF", "test $result")
                        val line = result.result.code
                        val name = result.result.name
                        val directions = result.result.directions
                        resultToPrint += "$line -- $name \n$directions"
                        resultTextView.text = resultToPrint
                        Log.d("EPF", "test $line $name")
                    }
                }
                "getSchedules" -> {
                    val station = stationInput.text.toString()
                    runBlocking {
                        val results = service.getSchedules("metros", line, station, "A+R")
                        Log.d("EPF", "test $results")
                        results.result.schedules.map {
                            val message = it.message
                            val destination = it.destination
                            resultToPrint += "$message -- Dir $destination \n"
                            resultTextView.text = resultToPrint
                            Log.d("EPF", "test $message $destination")
                        }
                    }
                }
                "getStations" -> {
                    runBlocking {
                        val results = service.getStations("metros", line)
                        Log.d("EPF", "test $results")
                        results.result.stations.map {
                            val name = it.name
                            resultToPrint += "$name \n"
                            resultTextView.text = resultToPrint
                            Log.d("EPF", "test $name")
                        }
                    }
                }
                else -> resultTextView.text = "Requete non valide"
            }
        }

    }
}

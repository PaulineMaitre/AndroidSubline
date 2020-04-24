package com.example.subline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.coroutines.runBlocking

class AppelApi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appel_api)

        var resultToPrint = ""
        var request = ""


        val pictoService = retrofit("picto").create(RatpPictoService::class.java)
        runBlocking {
            val pictoResult = pictoService.getPictoInfo("M14")
            Log.d("EPF", "test $pictoResult")
            resultToPrint += "$pictoResult"
            resultTextView.text = resultToPrint
            /*val fileName = pictoResult.records.fields.noms_des_fichiers.filename
            resultToPrint += "$fileName"
            testPicto.text = resultToPrint
            Log.d("EPF", "test $fileName")*/
            //imageView.setImageResource(fileName)
        }



        lineInput.isVisible = false
        stationInput.isVisible = false
        btnSearch.isVisible = false

        //print all lines to initialize activity
        /*val service = retrofit("transport").create(RatpService::class.java)
        runBlocking {
            val results = service.getAllMetroLines("metros")
            Log.d("EPF", "test $results")
            results.result.metros.map {
                val name = it.name
                //val directions = it.directions
                resultToPrint += "$name \n"
                resultTextView.text = resultToPrint
            }
        }*/

        // TEST GET ALL LINES (metros, rers, buses, tramways, noctiliens)
        /*val service = retrofit("transport").create(RatpService::class.java)
        runBlocking {
            val results = service.getAllLines()
            Log.d("EPF", "test $results")
            resultTextView.text = results.toString()
        }*/

        btnAllLines.setOnClickListener() {
            resultToPrint = ""
            request = "getAllLines"

            lineInput.isVisible = false
            stationInput.isVisible = false
            btnSearch.isVisible = false
            resultTextView.isVisible = true

            val service = retrofit("transport").create(RatpService::class.java)
            runBlocking {
                val results = service.getAllMetroLines("metros")
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

            val service = retrofit("transport").create(RatpService::class.java)

            when(request) {
                "getLineInfo" -> {
                    runBlocking {
                        val lineInfoResult = service.getLineInfo("metros", line)
                        Log.d("EPF", "test $lineInfoResult")
                        val line = lineInfoResult.result.code
                        val name = lineInfoResult.result.name
                        val directions = lineInfoResult.result.directions
                        val trafficInfoResult = service.getTrafficInfo("metros", line)
                        val title = trafficInfoResult.result.title
                        val message = trafficInfoResult.result.message
                        resultToPrint += "$line -- $name \n$directions\n"
                        resultToPrint += "Etat du traffic : $title\n$message"
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

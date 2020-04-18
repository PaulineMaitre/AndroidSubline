package com.example.subline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
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
            printRequest.text = "DEBUG " + request

            editText1.isVisible = false
            btnSearch.isVisible = false
            buttonSearch2.isVisible = false
            buttonSearch3.isVisible = false
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
            printRequest.text = "DEBUG " + request

            resultTextView.isVisible = false
            editText1.isVisible = true
            editText2.isInvisible = true
            //editText2.isVisible = false
            btnSearch.isVisible = true
            buttonSearch2.isVisible = false
            buttonSearch3.isVisible = false
        }

        btnSchedules.setOnClickListener {
            resultToPrint = ""
            request = "getSchedules"
            printRequest.text = "DEBUG " + request

            resultTextView.isVisible = false
            editText1.isVisible = true
            editText2.isVisible = true
            btnSearch.isVisible = false
            buttonSearch2.isVisible = true
            buttonSearch3.isVisible = false
        }

        btnStations.setOnClickListener {
            resultToPrint = ""
            request = "getStations"
            printRequest.text = "DEBUG " + request

            resultTextView.isVisible = false
            editText1.isVisible = true
            editText2.isInvisible = true
            //editText2.isVisible = false
            btnSearch.isVisible = false
            buttonSearch2.isVisible = false
            buttonSearch3.isVisible = true
        }

        btnSearch.setOnClickListener() {
            resultToPrint = ""

            resultTextView.isVisible = true

            val line = editText1.text.toString()

            val service = retrofit().create(RATPService::class.java)
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

        buttonSearch2.setOnClickListener() {
            resultToPrint = ""
            //val type = editText1.text.toString()
            val line = editText1.text.toString()
            val station = editText2.text.toString()

            val service = retrofit().create(RATPService::class.java)
            runBlocking {
                val results = service.getSchedules("metros", line, station, "A+R")
                Log.d("EPF", "test $results")
                results.result.schedules.map {
                    val id = it.code
                    val message = it.message
                    val destination = it.destination
                    resultToPrint += "$id -- $message -- Dir $destination \n"
                    resultTextView.text = resultToPrint
                    Log.d("EPF", "test $id $message $destination")
                }
            }
        }

        buttonSearch3.setOnClickListener() {
            resultToPrint = ""

            resultTextView.isVisible = true

            //val type = editText1.text.toString()
            val line = editText1.text.toString()

            val service = retrofit().create(RATPService::class.java)
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

    }
}

package com.example.subline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.subline.service.RATPService
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.coroutines.runBlocking

class AppelApi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appel_api)

        buttonSearch1.setOnClickListener() {
            var resultToPrint = ""
            val type = typeEditText.text.toString()
            val line = lineEditText.text.toString()

            val service = retrofit().create(RATPService::class.java)
            runBlocking {
                val result = service.getAllLines(type, line)
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
            var resultToPrint = ""
            val type = type2EditText.text.toString()
            val line = line2EditText.text.toString()
            val station = stationEditText.text.toString()

            val service = retrofit().create(RATPService::class.java)
            runBlocking {
                val results = service.getSchedules(type, line, station, "A") // renvoie un resultat vide wtf ??
                Log.d("EPF", "test $results")
                results.result.schedules.map {
                    val id = it.code
                    val message = it.message
                    val destination = it.destination
                    resultToPrint += "$id -- $message -- $destination \n"
                    resultTextView.text = resultToPrint
                }
            }
        }
    }
}

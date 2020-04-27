package com.example.subline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AppelApi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appel_api)

        var resultToPrint = ""
        var request = ""


        getLinePicto("M4")

        lineInput.isVisible = false
        stationInput.isVisible = false
        btnSearch.isVisible = false

        //print all lines to initialize activity // laisser en com vu que l'api est morte
       /* val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
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

        // TEST GET ALL LINES (metros, rers, buses, tramways, noctiliens) a garder pour après !!
        /*val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        runBlocking {
            val results = service.getAllLines()
            Log.d("EPF", "test $results")
            resultTextView.text = results.toString()
        }*/

        btnAllLines.setOnClickListener() {
            resultToPrint = ""
            request = "getAllLines"

            imageView.isVisible = false
            lineInput.isVisible = false
            stationInput.isVisible = false
            btnSearch.isVisible = false
            resultTextView.isVisible = true

            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
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

            imageView.isVisible = false
            resultTextView.isVisible = false
            lineInput.isVisible = true
            //lineInput.isInvisible = true
            stationInput.isVisible = false
            btnSearch.isVisible = true
        }

        btnSchedules.setOnClickListener {
            resultToPrint = ""
            request = "getSchedules"

            imageView.isVisible = false
            resultTextView.isVisible = false
            lineInput.isVisible = true
            stationInput.isVisible = true
            btnSearch.isVisible = true
        }

        btnStations.setOnClickListener {
            resultToPrint = ""
            request = "getStations"

            imageView.isVisible = false
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
            getLinePicto(line)

            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

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
                        resultToPrint += "Etat du trafic : $title\n$message"
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

    private fun getLinePicto(lineId: String) {
        val pictoService = retrofit(BASE_URL_PICTO).create(RatpPictoService::class.java)
        runBlocking {
            val pictoResult = pictoService.getPictoInfo(lineId)
            Log.d("EPF", "test $pictoResult")
            val id = pictoResult.records[0].fields.noms_des_fichiers.id
            val result = pictoService.getImage(id)
            result.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val stream: InputStream = response.body()!!.byteStream()
                    Sharp.loadInputStream(stream).into(imageView)
                    stream.close()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }
}

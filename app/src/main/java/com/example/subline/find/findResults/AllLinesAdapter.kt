package com.example.subline.find.findResults

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.list_line_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AllLinesAdapter (val lineCodes: List<String>, var stations: RecyclerView, val listStationsTextView: TextView, val transportType: String, val pictoLine: List<Int>) : RecyclerView.Adapter<AllLinesAdapter.LineViewHolder>() {

        class LineViewHolder(val lineView: View) : RecyclerView.ViewHolder(lineView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInflater.inflate(R.layout.list_line_item, parent,false)

            return LineViewHolder(
                view
            )
        }

        override fun getItemCount(): Int = lineCodes.size

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
            var lineCode = lineCodes[position]
            holder.lineView.lineIcon.setImageResource(pictoLine[position])

            holder.lineView.setOnClickListener {

                var listStations = if(lineCode == "C" || lineCode == "D") {
                    if(lineCode == "C"){
                        STATIONS_RER_C
                    } else {
                        STATIONS_RER_D
                    }
                } else {
                    getListOfStations(it, lineCode)
                }
                stations.adapter = AllStationsAdapter(listStations, pictoLine[position], lineCode, transportType)
            }
        }

        private fun getListOfStations(view: View, lineCode: String) : List<String> {
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            try {
                runBlocking {
                    val results = service.getStations(transportType, lineCode)
                    results.result.stations.map {
                        listStations.add(it.name)
                        listStations.sort()
                    }
                }
                listStationsTextView.isVisible = true
            } catch (e: retrofit2.HttpException) {
                listStationsTextView.isVisible = false
                Toast.makeText(view.context, R.string.lineError, Toast.LENGTH_LONG).show()
            }
            return listStations
        }



        private fun getLinePicto(lineId: String, imageview: ImageView) {

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
                        Sharp.loadInputStream(stream).into(imageview)
                        // stream.close()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }
        }


    }

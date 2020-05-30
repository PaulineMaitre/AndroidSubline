package com.example.subline.find.rers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.find.AllStationsAdapter
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import kotlinx.android.synthetic.main.list_line_item.view.*
import kotlinx.coroutines.runBlocking

class AllRersAdapter (val rers: List<String>, var stations: RecyclerView, val listStationsTextView: TextView, val transportType: String, val pictoLine: List<Int>) : RecyclerView.Adapter<AllRersAdapter.RersViewHolder>() {

        class RersViewHolder(val rersView: View) : RecyclerView.ViewHolder(rersView)
        /*val pictoRers = listOf<Int>(
            R.drawable.rera,
            R.drawable.rerb,
            R.drawable.rerc,
            R.drawable.rerd,
            R.drawable.rere
        )*/

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RersViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_line_item, parent,false)

            return RersViewHolder(view)
        }

        override fun getItemCount(): Int = rers.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: RersViewHolder, position: Int) {

            var rer = rers[position]

            holder.rersView.lineIcon.setImageResource(pictoLine[position])

            holder.rersView.setOnClickListener {
                var listStations: List<String> = emptyList()
                listStations = if(rer == "C" || rer == "D") {
                    if(rer == "C"){
                        STATIONSRERC
                    } else {
                        STATIONSRERD
                    }
                } else {
                    getListOfStations(it, rer)
                }
                stations.adapter = AllStationsAdapter(listStations, pictoLine[position], rer, transportType)
            }

        }

        private fun getListOfStations(view: View, rer: String) : List<String>{
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            try {
                runBlocking {
                    val results = service.getStations(transportType, rer)
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





        /*private fun getLinePicto(lineId: String, imageview: ImageView) {

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
        }*/


    }

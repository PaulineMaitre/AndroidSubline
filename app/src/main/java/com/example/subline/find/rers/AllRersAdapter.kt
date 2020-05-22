package com.example.subline.find.rers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.coroutines.runBlocking

class AllRersAdapter (val rers: List<String>, var stations: RecyclerView) : RecyclerView.Adapter<AllRersAdapter.RersViewHolder>() {

        class RersViewHolder(val rersView: View) : RecyclerView.ViewHolder(rersView)
        val pictoRers = listOf<Int>(
            R.drawable.rera,
            R.drawable.rerb,
            R.drawable.rerc,
            R.drawable.rerd,
            R.drawable.rere
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RersViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent,false)

            return RersViewHolder(view)
        }

        override fun getItemCount(): Int = rers.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: RersViewHolder, position: Int) {
            var rer = rers[position]
            holder.rersView.lineName.setImageResource(pictoRers[position])

            holder.rersView.setOnClickListener {
                var listStations: List<String> = emptyList()
                if(rer == "C" || rer == "D") {
                    if(rer == "C"){
                        listStations = STATIONSRERC
                    } else {
                        listStations = STATIONSRERD
                    }
                } else {
                    listStations = affiche_list_stations(rer)
                }

                stations.adapter = AllRerStationsAdapter(listStations, pictoRers[position], rer)
            }

        }

        fun affiche_list_stations(rer: String) : List<String>{
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            runBlocking {
                val results = service.getStations(TYPE_RER, rer)
                results.result.stations.map {
                    listStations.add(it.name)
                    listStations.sort()
                }
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

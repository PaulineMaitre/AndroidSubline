package com.example.subline.find.tram

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
import com.example.subline.find.metros.AllStationsAdapter
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.*
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AllTramsAdapter (val trams: List<String>, var stations: RecyclerView, val listStationsTextView: TextView) : RecyclerView.Adapter<AllTramsAdapter.TramsViewHolder>() {

        class TramsViewHolder(val tramsView: View) : RecyclerView.ViewHolder(tramsView)
        var pictoTrams = listOf<Int>(R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3a,
            R.drawable.t3b,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8,
            R.drawable.t11
            )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramsViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent,false)

            return TramsViewHolder(view)
        }

        override fun getItemCount(): Int = trams.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: TramsViewHolder, position: Int) {
            var tram = trams[position]
            holder.tramsView.lineName.setImageResource(pictoTrams[position])

            holder.tramsView.setOnClickListener {
                var listStations = getListOfStations(it, tram)
                stations.adapter = AllStationsAdapter(listStations, pictoTrams[position], tram, TYPE_TRAM)
            }

        }

        private fun getListOfStations(view: View, tram: String) : List<String>{
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            try {
                runBlocking {
                    val results = service.getStations(TYPE_TRAM, tram)
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

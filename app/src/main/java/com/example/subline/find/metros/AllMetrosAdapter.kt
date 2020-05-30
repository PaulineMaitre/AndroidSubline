package com.example.subline.find.metros

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
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_METRO
import com.example.subline.utils.retrofit
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.list_metro_item.view.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AllMetrosAdapter (val metros: List<String>, var stations: RecyclerView, val listStationsTextView: TextView) : RecyclerView.Adapter<AllMetrosAdapter.MetrosViewHolder>() {

        class MetrosViewHolder(val metrosView: View) : RecyclerView.ViewHolder(metrosView)
        var pictoMetros = listOf<Int>(R.drawable.m1,
            R.drawable.m2,
            R.drawable.m3,
            R.drawable.m3b,
            R.drawable.m4,
            R.drawable.m5,
            R.drawable.m6,
            R.drawable.m7,
            R.drawable.m7b,
            R.drawable.m8,
            R.drawable.m9,
            R.drawable.m10,
            R.drawable.m11,
            R.drawable.m12,
            R.drawable.m13,
            R.drawable.m14,
            R.drawable.mfun,
            R.drawable.orlyval
            )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetrosViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent,false)

            return MetrosViewHolder(view)
        }

        override fun getItemCount(): Int = metros.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: MetrosViewHolder, position: Int) {
            var metro = metros[position]
            holder.metrosView.lineName.setImageResource(pictoMetros[position])

            holder.metrosView.setOnClickListener {
                var listStations = getListOfStations(it, metro)
                stations.adapter = AllStationsAdapter(listStations, pictoMetros[position], metro, TYPE_METRO)
            }

        }

        private fun getListOfStations(view: View, metro: String) : List<String> {
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            try {
                runBlocking {
                    val results = service.getStations(TYPE_METRO, metro)
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

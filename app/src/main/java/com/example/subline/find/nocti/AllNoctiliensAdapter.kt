package com.example.subline.find.nocti

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
import com.example.subline.find.AllStationsAdapter
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

class AllNoctiliensAdapter (val noctiliens: List<String>, var stations: RecyclerView, val listStationsTextView: TextView) : RecyclerView.Adapter<AllNoctiliensAdapter.NoctiliensViewHolder>() {

        class NoctiliensViewHolder(val noctiliensView: View) : RecyclerView.ViewHolder(noctiliensView)
        var pictoNoctiliens = listOf<Int>(R.drawable.n01,
            R.drawable.n02,
            R.drawable.n11,
            R.drawable.n12,
            R.drawable.n13,
            R.drawable.n14,
            R.drawable.n15,
            R.drawable.n16,
            R.drawable.n21,
            R.drawable.n22,
            R.drawable.n23,
            R.drawable.n24,
            R.drawable.n31,
            R.drawable.n32,
            R.drawable.n33,
            R.drawable.n34,
            R.drawable.n35,
            R.drawable.n41,
            R.drawable.n42,
            R.drawable.n43,
            R.drawable.n44,
            R.drawable.n45,
            R.drawable.n51,
            R.drawable.n52,
            R.drawable.n53,
            R.drawable.n61,
            R.drawable.n62,
            R.drawable.n63,
            R.drawable.n66,
            R.drawable.n71,
            R.drawable.n122,
            R.drawable.n153
            )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoctiliensViewHolder {
            val layoutInfater: LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInfater.inflate(R.layout.list_metro_item, parent,false)

            return NoctiliensViewHolder(view)
        }

        override fun getItemCount(): Int = noctiliens.size


        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: NoctiliensViewHolder, position: Int) {
            var noctilien = noctiliens[position]
            holder.noctiliensView.lineName.setImageResource(pictoNoctiliens[position])

            holder.noctiliensView.setOnClickListener {
                var listStations = getListOfStations(it, noctilien)
                stations.adapter = AllStationsAdapter(
                    listStations,
                    pictoNoctiliens[position],
                    noctilien,
                    TYPE_NOCTI
                )
            }

        }

        private fun getListOfStations(view: View, noctilien: String) : List<String>{
            var listStations = arrayListOf<String>()
            val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
            try {
                runBlocking {
                    val results = service.getStations(TYPE_NOCTI, noctilien)
                    results.result.stations.map {
                        listStations.add(it.name)
                        listStations.sort()
                    }
                }
                listStationsTextView.isVisible = true
            } catch (e: retrofit2.HttpException) {
                listStationsTextView.isVisible = false
                Toast.makeText(view.context, R.string.lineError, Toast.LENGTH_SHORT).show()
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

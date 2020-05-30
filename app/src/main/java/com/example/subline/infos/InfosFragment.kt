package com.example.subline.infos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

import com.example.subline.R
import com.example.subline.data.TrafficResult
import com.example.subline.home.TrafficAdapter
import com.example.subline.service.RatpPictoService
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_PICTO
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.retrofit
import com.example.tripin.data.AppDatabase
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.activity_appel_api.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class InfosFragment : Fragment() {

    private lateinit var bt_metro : Button
    private lateinit var bt_rer : Button
    private lateinit var bt_tram : Button
    private lateinit var rv_traffic : RecyclerView
    val listMetro = arrayListOf<TrafficResult.Transport>()
    val listRer = arrayListOf<TrafficResult.Transport>()
    val listTram = arrayListOf<TrafficResult.Transport>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_infos, container, false)
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        rv_traffic = view.findViewById<RecyclerView>(R.id.homeTrafficRecyclerView)
        bt_metro = view.findViewById<Button>(R.id.cat_metro)
        bt_rer = view.findViewById<Button>(R.id.cat_rer)
        bt_tram = view.findViewById<Button>(R.id.cat_tram)

        var pictoMetros = listOf<Int>(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m3b, R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7, R.drawable.m7b, R.drawable.m8, R.drawable.m9, R.drawable.m10, R.drawable.m11, R.drawable.m12, R.drawable.m13, R.drawable.m14)
        var pictoTram = listOf<Int>(R.drawable.t1, R.drawable.t2, R.drawable.t3a, R.drawable.t3b, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8)
        var pictoRER = listOf<Int>(R.drawable.rera, R.drawable.rerb, R.drawable.rerc, R.drawable.rerd, R.drawable.rere)
        runBlocking {
            val results = service.getTrafficInfoC()
            var i = 0
            results.result.metros.map {
                val metro = TrafficResult.Transport("Metro",it.line, it.slug, it.title, it.message,pictoMetros[i])
                listMetro.add(metro)
                i++
            }
            i = 0
            results.result.rers.map {
                val rer = TrafficResult.Transport("RER",it.line, it.slug, it.title, it.message,pictoRER[i])
                listRer.add(rer)
                i++
            }
            i = 0
            results.result.tramways.map {
                val tram = TrafficResult.Transport("Tramway",it.line, it.slug, it.title, it.message,pictoTram[i])
                listTram.add(tram)
                i++
            }
        }
        var listTraffic = listMetro + listRer + listTram

        rv_traffic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_traffic.adapter = TrafficAdapter(listTraffic)

        listener_bouton(bt_metro, requireContext())
        listener_bouton(bt_rer, requireContext())
        listener_bouton(bt_tram, requireContext())



        return view
    }

    private fun listener_bouton(bt: Button, context: Context) {
        bt.setOnClickListener {
            var listTrafficchanged = listOf<TrafficResult.Transport>()
            if (bt.isActivated) {
                bt.isActivated = false
                bt.backgroundTintList =
                    context.getResources()!!.getColorStateList(R.color.Blank)
            } else {
                bt.isActivated = true
                bt.backgroundTintList =
                    context.getResources()!!.getColorStateList(R.color.butn_pressed)
            }

            if (bt_metro.isActivated == false && bt_rer.isActivated == false && bt_tram.isActivated == false) {
                listTrafficchanged = listMetro + listRer + listTram
            } else {
                if (bt_metro.isActivated) {
                    listTrafficchanged = listMetro
                }
                if (bt_rer.isActivated) {
                    listTrafficchanged += listRer
                }

                if (bt_tram.isActivated) {
                    listTrafficchanged += listTram
                }
            }

            rv_traffic.adapter = TrafficAdapter(listTrafficchanged)

        }


    }

        private fun getLinePicto(lineId: String) {
        val pictoService = retrofit(BASE_URL_PICTO).create(RatpPictoService::class.java)
        runBlocking {
            val pictoResult = pictoService.getPictoInfo("M1")
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

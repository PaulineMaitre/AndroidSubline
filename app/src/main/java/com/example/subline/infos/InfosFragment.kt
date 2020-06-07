package com.example.subline.infos

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subline.R
import com.example.subline.data.TrafficResult
import com.example.subline.utils.*
import com.example.subline.service.RatpService
import kotlinx.coroutines.runBlocking

class InfosFragment : Fragment() {

    private lateinit var metroTrafficButton : Button
    private lateinit var rerTrafficButton : Button
    private lateinit var tramTrafficButton : Button
    private lateinit var trafficRecyclerView : RecyclerView
    private val listMetro = arrayListOf<TrafficResult.Transport>()
    private val listRer = arrayListOf<TrafficResult.Transport>()
    private val listTram = arrayListOf<TrafficResult.Transport>()
    private var displayList = ArrayList<String>()
    private lateinit var adapter : TrafficAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_infos, container, false)
        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)

        trafficRecyclerView = view.findViewById(R.id.homeTrafficRecyclerView)
        metroTrafficButton = view.findViewById(R.id.metroTrafficButton)
        rerTrafficButton = view.findViewById(R.id.rerTrafficButton)
        tramTrafficButton = view.findViewById(R.id.tramTrafficButton)

        runBlocking {
            val results = service.getTrafficInfoC()
            var i = 0
            results.result.metros.map {
                val metro = TrafficResult.Transport("Metro", it.line, it.slug, it.title, it.message, PICTO_METRO[i])
                listMetro.add(metro)
                displayList.add("Métro ${it.line}")
                i++
            }
            i = 0
            results.result.rers.map {
                val rer = TrafficResult.Transport("RER", it.line, it.slug, it.title, it.message, PICTO_RER[i])
                listRer.add(rer)
                displayList.add("RER ${it.line}")
                i++
            }
            i = 0
            results.result.tramways.map {
                val tram = TrafficResult.Transport("Tramway", it.line, it.slug, it.title, it.message, PICTO_TRAM[i])
                listTram.add(tram)
                displayList.add("Tram ${it.line}")
                i++
            }
        }
        var listTraffic = listMetro + listRer + listTram
        adapter = TrafficAdapter(listTraffic.toMutableList())

        trafficRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        trafficRecyclerView.adapter =
            TrafficAdapter(listTraffic.toMutableList())

        buttonListener(metroTrafficButton, requireContext())
        buttonListener(rerTrafficButton, requireContext())
        buttonListener(tramTrafficButton, requireContext())

        return view
    }

    private fun buttonListener(button: Button, context: Context) {
        button.setOnClickListener {
            var listTrafficChanged = listOf<TrafficResult.Transport>()
            if (button.isActivated) {
                button.isActivated = false
                button.backgroundTintList =
                    context.resources!!.getColorStateList(R.color.Blank)
            } else {
                button.isActivated = true
                button.backgroundTintList =
                    context.resources!!.getColorStateList(R.color.buttonPressed)
            }

            if (!metroTrafficButton.isActivated && !rerTrafficButton.isActivated && !tramTrafficButton.isActivated) {
                listTrafficChanged = listMetro + listRer + listTram
            } else {
                if (metroTrafficButton.isActivated) {
                    listTrafficChanged = listMetro
                }
                if (rerTrafficButton.isActivated) {
                    listTrafficChanged += listRer
                }

                if (tramTrafficButton.isActivated) {
                    listTrafficChanged += listTram
                }
            }
            trafficRecyclerView.adapter =
                TrafficAdapter(listTrafficChanged.toMutableList())
        }
    }

}

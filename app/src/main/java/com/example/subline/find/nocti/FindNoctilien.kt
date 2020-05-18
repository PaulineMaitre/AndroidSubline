package com.example.subline.find.nocti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.subline.R
import com.example.subline.service.RatpService
import com.example.subline.utils.BASE_URL_TRANSPORT
import com.example.subline.utils.TYPE_NOCTI
import com.example.subline.utils.retrofit
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FindNoctilien : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_find_nocti, container, false)
        val allNoctisRv = view.findViewById<RecyclerView>(R.id.allNoctisRv)
        val allNoctiStationsRv = view.findViewById<RecyclerView>(R.id.allNoctiStationsRv)

        val service = retrofit(BASE_URL_TRANSPORT).create(RatpService::class.java)
        var noctiliens: ArrayList<String> = ArrayList()
        runBlocking {
            val results = service.getLinesByType(TYPE_NOCTI)
            results.result.noctiliens.map {
                if(!noctiliens.contains(it.code)) {
                    noctiliens.add(it.code)
                //noctiliens.sort()
                }
            }
            //noctiliens.sort()
            //Log.d("EPF", "${noctiliens}")
        }

        allNoctisRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        allNoctiStationsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        allNoctisRv.adapter = AllNoctiliensAdapter(noctiliens, allNoctiStationsRv)
        return view
    }

}
